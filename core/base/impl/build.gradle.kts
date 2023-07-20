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

        val androidMain by getting {
            dependencies {
                implementation(libs.sqldelight.android)
            }
        }

        val desktopMain by getting {
            dependencies {
                implementation(libs.sqldelight.sqlite)
            }
        }

        val jsMain by getting {
            dependencies {
                implementation(libs.sqldelight.web)
                implementation(devNpm("copy-webpack-plugin", "9.1.0"))
            }
        }
    }
}

sqldelight {
    databases {
        create("MissionDatabase") {
            packageName.set("ru.kyamshanov.mission.core.database")
            generateAsync.set(true)
        }
    }
}