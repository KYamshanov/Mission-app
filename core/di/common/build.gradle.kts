plugins {
    id("multiplatform-lib")
}

kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(projects.core.di.impl)
            }
        }
    }
}