plugins {
    id("kotlin-api")
    alias(libs.plugins.kotlinx.serialization)
}

dependencies {
    api(projects.core.sessionFront.api)
    api(projects.core.di.impl)
    implementation(projects.core.network.api)
    implementation(projects.core.base.api)
    implementation(libs.ktor.serialization.json)
}