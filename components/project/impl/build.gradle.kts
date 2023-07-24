plugins {
    id("multiplatform-ui-lib")
}

dependencies {
    commonMainApi(projects.components.project.api)
    commonMainImplementation(projects.core.network.api)
    commonMainImplementation(projects.core.sessionFront.api)
    commonMainImplementation(projects.core.base.api)
    commonMainImplementation(projects.components.time)
}