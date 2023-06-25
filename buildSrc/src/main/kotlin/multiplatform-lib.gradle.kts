import org.gradle.accessors.dm.LibrariesForLibs
import org.jetbrains.compose.desktop.application.dsl.TargetFormat

//see https://github.com/gradle/gradle/issues/15383
val libs = the<LibrariesForLibs>()

plugins {
    id("multiplatform-compose")
}

kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(libs.voyager)
            }
        }
    }
}