plugins {
    id("kotlin-api")
}

dependencies {
    commonMainApi(projects.core.di.api)
    commonMainApi(libs.koin.core)
}

