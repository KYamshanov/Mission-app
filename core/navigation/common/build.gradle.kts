plugins {
    id("kotlin-api")
}

dependencies {
    api(libs.voyager)
    implementation(projects.core.navigation.api)
}