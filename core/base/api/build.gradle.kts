plugins {
    id("kotlin-api")
}

dependencies {
    commonMainImplementation(projects.core.di.api)
    commonMainImplementation(libs.moko.resources.core)
}
