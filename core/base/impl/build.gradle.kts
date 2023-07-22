plugins {
    id("multiplatform-lib")
    alias(libs.plugins.sqldelight)
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