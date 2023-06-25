plugins {
    id("multiplatform-lib")
    alias(libs.plugins.kotlinx.serialization)
}


kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {
                api(projects.core.network.api)
                implementation(projects.core.di.impl)
                implementation(projects.core.sessionFront.api)
                implementation(libs.ktor.core)
                implementation(libs.napier)
                implementation(libs.ktor.client.negotiation)
                implementation(libs.ktor.serialization.json)
                implementation(libs.ktor.client.logging)
                implementation("io.ktor:ktor-client-android:2.3.0")
            }
        }

        val androidMain by getting {
            dependencies {
                implementation(libs.ktor.client.okhttp)
            }
        }

        val desktopMain by getting {
            dependencies {
                implementation(libs.ktor.client.okhttp)
            }
        }

    }
}