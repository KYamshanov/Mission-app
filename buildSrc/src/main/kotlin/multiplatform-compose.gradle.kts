import org.gradle.accessors.dm.LibrariesForLibs
import org.jetbrains.compose.desktop.application.dsl.TargetFormat

//see https://github.com/gradle/gradle/issues/15383
val libs = the<LibrariesForLibs>()

plugins {
    id("multiplatform-base")
    id("android-library")
    id("org.jetbrains.compose")
}

kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.material)
                @OptIn(org.jetbrains.compose.ExperimentalComposeLibrary::class)
                implementation(compose.components.resources)
            }
        }

        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
    }
}