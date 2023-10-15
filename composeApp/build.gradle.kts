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

        val commonMain by getting {

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
                implementation(compose.foundation)
                implementation(compose.material)
            }
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

        manifestPlaceholders["appAuthRedirectScheme"] = "ru.kts.oauth"
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