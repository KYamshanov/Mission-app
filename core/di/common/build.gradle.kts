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
                implementation(projects.core.base.impl)
                implementation(projects.core.sessionFront.impl)
                implementation(projects.core.network.impl)
            }
        }
    }
}