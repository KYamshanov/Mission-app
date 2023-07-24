plugins {
    id("multiplatform-ui-lib")
}

kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {
                api(projects.core.base.api)
                implementation(projects.core.di.common)
                implementation(projects.core.platformBase)
            }
        }
    }
}