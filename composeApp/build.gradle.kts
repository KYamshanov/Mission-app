plugins {
    id("multiplatform-application")
}

kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(projects.core.ui)
            }
        }
    }
}