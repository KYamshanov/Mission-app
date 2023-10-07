import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    kotlin("multiplatform")
    id("org.openjfx.javafxplugin") version "0.0.10"
    id("org.jetbrains.compose")
}

group = "com.example.webview"
version = "1.0.0"

kotlin {
    jvm {
        compilations.all {
            kotlinOptions {
                jvmTarget = "17"
            }
            withJava()
        }
    }

    sourceSets {

        val commonMain by getting {
            dependencies {

            }
        }

        val jvmMain by getting {
            dependencies {
                api(projects.core.authorization.api)
                implementation(libs.kotlinx.coroutines.core)
                implementation(libs.napier)
                implementation(libs.decompose.core)
                implementation(projects.core.di.common)
                implementation(projects.core.navigation.impl)
                implementation(projects.core.platformBase)
                implementation(libs.decompose.core)
                implementation(projects.core.oauth2.api)
                implementation(projects.core.sessionFront.api)

                implementation(compose.foundation)
                implementation(compose.desktop.common)
                implementation(compose.desktop.currentOs)
                implementation(libs.ktor.client.okhttp)
                implementation(libs.kotlinx.coroutines.swing)
                implementation(libs.decompose.compose)
                implementation(compose.material)
            }
        }
        val jvmTest by getting {
            dependencies {
                // testing deps
            }
        }
    }
}

javafx {
    version = "16"
    modules = listOf("javafx.controls", "javafx.swing", "javafx.web", "javafx.graphics")
}