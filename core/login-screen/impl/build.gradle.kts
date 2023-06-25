plugins {
    id("multiplatform-ui-lib")
}

kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {
                api(projects.core.loginScreen.api)
            }
        }
    }
}