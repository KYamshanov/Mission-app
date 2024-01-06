plugins {
    id("multiplatform-lib")
    id("multiplatform-network")
}


kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {
                api(projects.core.network.api)
                implementation(projects.core.di.common)
                implementation(projects.core.sessionFront.api)
                implementation(libs.ktor.core)
                implementation(libs.ktor.client.negotiation)
                implementation(libs.ktor.client.logging)
                implementation(libs.ktor.client.auth)
            }
        }

        val androidMain by getting {
            dependencies {
                implementation(libs.ktor.client.okhttp)
            }
        }

        val desktopMain by getting {
            dependencies {
                implementation(libs.ktor.client.cio)
            }
        }

        val jsMain by getting {
            dependencies {
                implementation(libs.ktor.client.js)
            }
        }
    }
}