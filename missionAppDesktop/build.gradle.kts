import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
    id("org.jetbrains.kotlin.plugin.serialization")
}

group = "ru.kyamshanov.mission"
version = "0.0.1"

kotlin {
    jvm("desktop")

    sourceSets {

        val commonMain by getting {
            dependencies {
                implementation(libs.decompose.core)
                implementation(libs.ktor.serialization.json)
                implementation(libs.kotlinx.serialization.json)
            }
        }

        val desktopMain by getting {
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
            packageName = "MissionApp"
            packageVersion = "1.0.0"
            description = "Mission application"
            copyright = "© 2023 KYamshanov. All rights reserved."
            vendor = "KYamshanov"

            windows {
                menu = true
                iconFile.set(project.file("icons/app_icon.ico"))
            }
        }
        buildTypes.release.proguard {
            obfuscate.set(true)
            configurationFiles.from("proguard-rules.pro")
        }
    }
}