import org.gradle.accessors.dm.LibrariesForLibs

//see https://github.com/gradle/gradle/issues/15383
val libs = the<LibrariesForLibs>()

plugins {
    id("org.jetbrains.kotlin.multiplatform")
    id("org.jetbrains.compose")
    id("dev.icerock.mobile.multiplatform-resources")
}


kotlin {
    js(IR) {
        browser()
        binaries.executable()
    }

    sourceSets {
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

        val jsMain by getting {
            dependencies {
                implementation(compose.html.core)
                implementation(compose.runtime)
                implementation(libs.kotlinx.coroutines.core)
                implementation(libs.napier)
                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.material)
                @OptIn(org.jetbrains.compose.ExperimentalComposeLibrary::class)
                implementation(compose.components.resources)

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
    }
}

multiplatformResources {
    multiplatformResourcesPackage = "ru.kyamshanov.mission"
    multiplatformResourcesClassName = "Res"
}