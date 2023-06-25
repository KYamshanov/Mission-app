plugins {
    id("kotlin-api")
    id("network")
}

dependencies {
    api(projects.core.sessionFront.api)
    api(projects.core.di.impl)
    implementation(projects.core.network.api)
    implementation(projects.core.base.api)
}