plugins {
    id("kotlin-api")
}
dependencies {
    commonMainImplementation(projects.core.di.api)
    commonMainApi(libs.decompose.core)
}