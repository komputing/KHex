apply {
    from("https://raw.githubusercontent.com/ligi/gradle-common/master/versions_plugin_stable_only.gradle")
}

buildscript {
    repositories {
        jcenter()
    }

    dependencies {
        classpath("com.github.ben-manes:gradle-versions-plugin:${Versions.versionsPlugin}")
    }
}

allprojects {

    repositories {
        jcenter()
        maven("https://jitpack.io")
    }

}

subprojects {
    repositories {
        jcenter()
        maven("https://jitpack.io")
        maven("https://kotlin.bintray.com/kotlinx")
    }
}

