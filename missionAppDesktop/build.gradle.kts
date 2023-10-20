import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
}

group = "ru.kyamshanov.mission"
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
                implementation(libs.kotlinx.coroutines.core)
                implementation(libs.napier)
                implementation(libs.decompose.core)
                implementation(projects.core.di.bundle)
                implementation(projects.core.di.common)
                implementation(projects.core.navigation.impl)
                implementation(projects.core.theme)
                implementation(projects.core.sessionFront.api)
                implementation(projects.foundation.api)
                implementation(projects.core.platformBase)
                implementation(libs.decompose.core)
                implementation(projects.core.oauth2.api)
                implementation(projects.core.ui)

                implementation(compose.foundation)
                implementation(compose.desktop.common)
                implementation(compose.desktop.currentOs)
                implementation(libs.ktor.client.okhttp)
                implementation(libs.kotlinx.coroutines.swing)
                implementation(libs.decompose.compose)
                implementation(compose.material)
                implementation(libs.moko.resources.compose)
            }
        }
        val jvmTest by getting {
            dependencies {
                // testing deps
            }
        }
    }
}


compose.desktop {
    application {
        mainClass = "MainKt"

        nativeDistributions {
            targetFormats(
                TargetFormat.Dmg,
                TargetFormat.Msi,
                TargetFormat.Deb,
                TargetFormat.Exe
            )
            packageName = "ru.kyamshanov.mission.desktopApp"
            packageVersion = "1.0.0"
        }
    }
}