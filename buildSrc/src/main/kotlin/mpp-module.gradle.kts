import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask
import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.tasks.testing.logging.TestLogEvent

plugins {
    kotlin("multiplatform")
    id("java-library") // required for jacoco plugin
    jacoco
    id("com.github.ben-manes.versions")
}

kotlin {
    explicitApi()
    targets {
        jvm {
            compilations.all {
                kotlinOptions.jvmTarget = "1.8"
            }
        }
        js(BOTH) {
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
        xml.isEnabled = true
        csv.isEnabled = false
        html.isEnabled = true
        html.destination =
            File("${buildDir}/jacoco-reports/html")
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