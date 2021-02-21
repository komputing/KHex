plugins {
    id("mpp-module")
}

kotlin {
    sourceSets {
        commonMain {
            dependencies {
                implementation(project(":core"))
            }
        }
        commonTest {
            dependencies {
                implementation("com.willowtreeapps.assertk:assertk:${Versions.assertk}")
            }
        }
    }
}