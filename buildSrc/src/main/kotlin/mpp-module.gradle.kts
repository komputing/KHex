import org.gradle.kotlin.dsl.kotlin

plugins {
    kotlin("multiplatform")
}

kotlin {
    targets {
        jvm()
        js(BOTH) {
            nodejs {
                testTask {
                    useMocha()
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
