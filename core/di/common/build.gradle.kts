plugins {
    id("multiplatform-lib")
}

kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(projects.core.di.impl)

                implementation(projects.core.splashScreen)
                implementation(projects.core.navigation.impl)
                implementation(projects.core.loginScreen.impl)
            }
        }
    }
}