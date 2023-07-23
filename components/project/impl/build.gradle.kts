plugins {
    id("multiplatform-ui-lib")
}

dependencies {
    commonMainApi(projects.components.project.api)
    commonMainImplementation(projects.core.network.api)
}