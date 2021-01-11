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

