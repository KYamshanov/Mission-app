plugins {
    id("multiplatform-application")
}

kotlin {
    sourceSets {

        val androidMain by getting {
            dependencies {
                implementation(libs.androidx.compose.viewmodel)
                implementation(libs.decompose.android)
                implementation(libs.decompose.compose)
            }
        }
    }
}

dependencies {
    commonMainImplementation(projects.core.di.bundle)
    commonMainImplementation(projects.core.di.common)
    commonMainImplementation(projects.core.navigation.impl)
    commonMainImplementation(projects.core.theme)
    commonMainImplementation(projects.core.sessionFront.api)
    commonMainImplementation(projects.foundation.api)
    commonMainImplementation(projects.core.platformBase)
    commonMainImplementation(libs.decompose.core)
}