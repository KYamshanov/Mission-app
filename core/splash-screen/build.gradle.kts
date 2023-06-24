plugins {
    id("multiplatform-compose")
}

kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(projects.core.theme)
                implementation(projects.core.ui)
            }
        }
    }
}