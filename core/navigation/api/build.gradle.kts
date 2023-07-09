plugins {
    id("multiplatform-lib")
}
dependencies {
    commonMainImplementation(projects.core.di.api)
    commonMainApi(libs.decompose.core)
    commonMainApi(libs.decompose.compose)
}