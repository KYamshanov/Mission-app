import gradle.kotlin.dsl.accessors._d0abc96ef935f83d39c328e22f847631.sourceSets
import gradle.kotlin.dsl.accessors._d4133f3c36cd1fd4a38fc6d50db50cdf.compose
import org.gradle.accessors.dm.LibrariesForLibs
import org.jetbrains.compose.desktop.application.dsl.TargetFormat

//see https://github.com/gradle/gradle/issues/15383
val libs = the<LibrariesForLibs>()

plugins {
    id("org.jetbrains.kotlin.multiplatform")
    id("com.android.application")
    id("org.jetbrains.compose")
    id("kotlin-parcelize")
}

kotlin {
    sourceSets {
        android {
            compilations.all {
                kotlinOptions {
                    jvmTarget = "1.8"
                }
            }
        }
        jvm("desktop")

        js(IR) {
            browser()
            binaries.executable()
        }

        val commonMain by getting {

            dependencies {
                implementation(libs.kotlinx.coroutines.core)
                implementation(libs.napier)
                implementation(compose.foundation)
                implementation(compose.material)
                implementation(libs.decompose.core)
            }
        }

        val androidMain by getting {
            dependencies {
                implementation(libs.androidx.appcompat)
                implementation(libs.androidx.activityCompose)
                implementation(libs.compose.uitooling)
                implementation(libs.kotlinx.coroutines.android)
                implementation(libs.ktor.client.okhttp)
                implementation(libs.decompose.android)
                implementation(libs.decompose.compose)
            }
        }

        val desktopMain by getting {
            dependencies {
                implementation(compose.desktop.common)
                implementation(compose.desktop.currentOs)
                implementation(libs.ktor.client.okhttp)
                implementation(libs.kotlinx.coroutines.swing)
                implementation(libs.decompose.compose)
            }
        }

    }
}


compose.desktop {
    application {
        mainClass = "MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "ru.kyamshanov.mission.desktopApp"
            packageVersion = "1.0.0"
        }
    }
}


android {
    namespace = "ru.kyamshanov.mission"
    compileSdk = Versions.CompileSDK

    defaultConfig {
        minSdk = Versions.MinSDK
        targetSdk = Versions.CompileSDK
        applicationId = "ru.kyamshanov.mission"

        versionCode = Versions.VersionCode
        versionName = Versions.VersionName
    }
    sourceSets["main"].apply {
        manifest.srcFile("src/androidMain/AndroidManifest.xml")
        res.srcDirs("src/androidMain/resources")
        resources.srcDirs("src/commonMain/resources")
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    packaging {
        resources.excludes.add("META-INF/**")
    }
}