import org.gradle.accessors.dm.LibrariesForLibs

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