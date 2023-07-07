plugins {
    id("kotlin-api")
}

dependencies {
    implementation(projects.core.navigation.impl)
    implementation(projects.foundation.impl)
    implementation(projects.core.base.impl)
    implementation(projects.core.sessionFront.impl)
    implementation(projects.core.network.impl)
    implementation(projects.components.mainScreen.impl)
}