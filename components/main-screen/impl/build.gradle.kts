plugins {
    id("multiplatform-ui-lib")
}

dependencies {
    commonMainApi(projects.components.mainScreen.api)
    commonMainImplementation(projects.components.project.api)
    commonMainImplementation(projects.core.sessionFront.api)
    commonMainImplementation(projects.components.point.api)
}