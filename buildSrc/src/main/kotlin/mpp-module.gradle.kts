import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask
import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.tasks.testing.logging.TestLogEvent
import java.lang.System.getenv

plugins {
    kotlin("multiplatform")
    id("java-library") // required for jacoco plugin
    jacoco
    id("com.github.ben-manes.versions")
    id("maven-publish")
}

val darwinTargets = arrayOf(
    "macosX64", "macosArm64",
    "iosArm64", "iosX64", "iosSimulatorArm64",
    "tvosArm64", "tvosX64", "tvosSimulatorArm64",
    "watchosArm32", "watchosArm64", "watchosX64", "watchosSimulatorArm64",
)
val linuxTargets = arrayOf("linuxX64", "linuxArm64")
val mingwTargets = arrayOf("mingwX64")
val nativeTargets = linuxTargets + darwinTargets + mingwTargets


kotlin {
    explicitApi()
    targets {
        jvm {
            withJava() // required for jacoco plugin
            compilations.all {
                kotlinOptions.jvmTarget = "1.8"
            }
        }
        js(IR) {
            compilations {
                this.forEach { compilation ->
                    compilation.compileKotlinTask.kotlinOptions.apply {
                        sourceMap = true
                        moduleKind = "umd"
                        metaInfo = true
                        sourceMapEmbedSources = "always"

                        if (compilation.name == "main") main = "noCall"
                    }
                }
            }
            nodejs {
                testTask {
                    useMocha {
                        timeout = "10s"
                    }
                }
            }
        }
        for (target in nativeTargets) {
            targets.add(presets.getByName(target).createTarget(target))
        }
    }

    sourceSets {
        commonTest {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }

        val jvmTest by getting {
            dependencies {
                implementation(kotlin("test-junit"))
            }
        }

        val jsTest by getting {
            dependencies {
                implementation(kotlin("test-js"))
            }
        }
        all {
            languageSettings.enableLanguageFeature("InlineClasses")
        }
    }
}

tasks {
    withType<AbstractTestTask> {
        testLogging {
            events = setOf(TestLogEvent.FAILED, TestLogEvent.PASSED, TestLogEvent.SKIPPED)
            showExceptions = true
            exceptionFormat = TestExceptionFormat.FULL
        }
    }
}

jacoco {
    toolVersion = Versions.jacocoPlugin
}

tasks.withType<JacocoReport> {
    dependsOn("jvmTest")
    group = "Reporting"
    description = "Generate Jacoco coverage reports."
    val coverageSourceDirs = arrayOf(
        "src/commonMain",
        "src/jvmMain"
    )
    val classFiles = File("${buildDir}/classes/kotlin/jvm/main")
        .walkBottomUp()
        .toSet()
    classDirectories.setFrom(classFiles)
    sourceDirectories.setFrom(files(coverageSourceDirs))
    additionalSourceDirs.setFrom(files(coverageSourceDirs))

    executionData
        .setFrom(files("${buildDir}/jacoco/jvmTest.exec"))
    reports {
        xml.required.set(true)
        csv.required.set(false)
        html.required.set(true)
        html.outputLocation.set(
            File("${buildDir}/jacoco-reports/html")
        )
    }
}

/**
 * The Gradle Versions Plugin is another Gradle plugin to discover dependency updates
 * plugins.id("com.github.ben-manes.versions")
 * Run it with $ ./gradlew --scan dependencyUpdates
 * https://github.com/ben-manes/gradle-versions-plugin
 * **/
tasks.named("dependencyUpdates", DependencyUpdatesTask::class).configure {
    fun isNonStable(version: String): Boolean {
        val stableKeyword = listOf("RELEASE", "FINAL", "GA").any { version.toUpperCase().contains(it) }
        val regex = "^[0-9,.v-]+(-r)?$".toRegex()
        val isStable = stableKeyword || regex.matches(version)
        return isStable.not()
    }

    rejectVersionIf {
        isNonStable(candidate.version)
    }
    checkConstraints = true
}

getenv("GITHUB_REPOSITORY")?.let { githubRepo ->
    val (owner, repoName) = githubRepo.split('/').map(String::toLowerCase)
    group = "com.github.$owner.$repoName"
    version = System.getProperty("version")
    publishing {
        repositories {
            maven {
                name = "github"
                url = uri("https://maven.pkg.github.com/$githubRepo")
                credentials(PasswordCredentials::class)
            }
        }
    }
}
