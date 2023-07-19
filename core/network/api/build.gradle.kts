plugins {
    id("kotlin-api")
}

dependencies {
    commonMainApi(libs.ktor.core)
    commonMainImplementation(projects.core.di.api)
}
