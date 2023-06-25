plugins {
    id("kotlin-api")
}

dependencies {
    api(libs.ktor.core)
    implementation(projects.core.di.api)
}
