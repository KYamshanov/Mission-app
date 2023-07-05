plugins {
    id("kotlin-api")
}

dependencies {
    api(projects.core.di.api)
    api(libs.koin.core)
}

