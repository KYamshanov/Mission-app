plugins {
    id("multiplatform-ui-lib")
}

kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(projects.core.theme)
                implementation(projects.core.ui)
                implementation(projects.core.loginScreen.impl)
                implementation(projects.core.navigation.impl)
                implementation(projects.core.navigation.common)
                implementation(projects.core.di.impl)
            }
        }
    }
}