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
                implementation(libs.androidx.compose.viewmodel)
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
            targetFormats(org.jetbrains.compose.desktop.application.dsl.TargetFormat.Dmg, org.jetbrains.compose.desktop.application.dsl.TargetFormat.Msi, org.jetbrains.compose.desktop.application.dsl.TargetFormat.Deb)
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



dependencies {
    commonMainImplementation(projects.core.di.bundle)
    commonMainImplementation(projects.core.di.common)
    commonMainImplementation(projects.core.navigation.impl)
    commonMainImplementation(projects.core.theme)
    commonMainImplementation(projects.core.sessionFront.api)
    commonMainImplementation(projects.foundation.api)
    commonMainImplementation(projects.core.platformBase)
    commonMainImplementation(libs.decompose.core)
}