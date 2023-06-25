plugins {
    id("multiplatform-lib")
}

kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {
                api(projects.core.loginScreen.api)
                implementation(projects.core.theme)
                implementation(projects.core.ui)
            }
        }
    }
}