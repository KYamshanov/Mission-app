plugins {
    id("multiplatform-lib")
}
dependencies {
    commonMainImplementation(projects.core.di.api)
    commonMainImplementation(libs.decompose.core)
}
