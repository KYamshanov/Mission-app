plugins {
    id("multiplatform-compose")
}

kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {
                api(projects.core.base.api)
                implementation(projects.core.di.common)
            }
        }

        val desktopMain by getting {
            dependencies {
                implementation(compose.desktop.common)
                implementation(compose.desktop.currentOs)
            }
        }
    }
}