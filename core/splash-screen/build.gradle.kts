plugins {
    id("multiplatform-lib")
}

kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(projects.core.theme)
                implementation(projects.core.ui)
                implementation(projects.core.loginScreen.impl)
            }
        }
    }
}