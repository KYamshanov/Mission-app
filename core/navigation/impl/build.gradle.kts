plugins {
    id("multiplatform-ui-lib")
}

kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {
                api(projects.core.navigation.api)
                api(projects.core.navigation.common)
                implementation(projects.core.di.common)
                implementation(libs.voyager)
                implementation(libs.decompose.core)
                implementation(libs.decompose.compose)
            }
        }
    }
}