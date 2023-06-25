plugins {
    id("multiplatform-application")
}

kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(projects.core.splashScreen)
                implementation(projects.core.di.common)
            }
        }
    }
}