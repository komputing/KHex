plugins {
    base
    id("org.jetbrains.dokka") version "1.9.0"
}

val testAggregateReport = tasks.register<TestReport>("testAggregateReport") {
    group = "Reporting"
    description = "Collect aggregate test reports of all sub-modules."
    destinationDir = file("$buildDir/reports/tests")
    reportOn(subprojects.map {
        it.tasks.withType<AbstractTestTask>()
    })
}

subprojects {
    apply(plugin = "org.jetbrains.dokka")
    afterEvaluate {
        tasks.withType<AbstractTestTask> {
            finalizedBy(testAggregateReport)
        }
    }
}