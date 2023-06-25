plugins {
    id("multiplatform-ui-lib")
}

kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {
                api(projects.core.loginScreen.api)
                implementation(projects.core.di.impl)
                implementation(projects.core.theme)
                implementation(projects.core.ui)
                implementation(projects.core.navigation.api)
                implementation(projects.core.navigation.common)
                implementation(libs.voyager)
            }
        }
    }
}