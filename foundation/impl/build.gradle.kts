plugins {
    id("multiplatform-ui-lib")
}

dependencies {
    commonMainApi(projects.foundation.api)

    commonMainImplementation(libs.moko.mvvm)

    commonMainImplementation(projects.core.navigation.common)
    commonMainImplementation(projects.core.di.common)
    commonMainImplementation(projects.core.theme)
    commonMainImplementation(projects.core.sessionFront.api)
}