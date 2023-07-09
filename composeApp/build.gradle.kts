plugins {
    id("multiplatform-application")
}

dependencies {
    commonMainImplementation(projects.core.di.bundle)
    commonMainImplementation(projects.core.di.common)
    commonMainImplementation(projects.core.navigation.impl)
    commonMainImplementation(projects.foundation.api)
    commonMainImplementation(projects.core.platformBase)
    commonMainImplementation(libs.decompose.core)
    commonMainImplementation(libs.decompose.compose)
}