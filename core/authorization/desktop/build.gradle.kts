plugins {
    id("multiplatform-lib")
}

dependencies {
    commonMainApi(projects.core.authorization.api)
    commonMainImplementation(projects.core.di.common)
    commonMainImplementation(projects.core.oauth2.api)
    commonMainImplementation(projects.core.navigation.api)
    commonMainImplementation(projects.core.sessionFront.api)
}