import org.gradle.accessors.dm.LibrariesForLibs

//see https://github.com/gradle/gradle/issues/15383
val libs = the<LibrariesForLibs>()

plugins {
    id("org.jetbrains.kotlin.multiplatform")
    id("com.android.application")
}


kotlin {
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
