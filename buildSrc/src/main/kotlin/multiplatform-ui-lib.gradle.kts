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
                implementation(project(":core:ui"))
                implementation(project(":core:di:common"))
                implementation(project(":core:navigation:api"))
                implementation(project(":core:navigation:common"))

                api(libs.decompose.core)
            }
        }

        val commonUiMain by getting {
            dependencies {
                api(libs.decompose.compose)
            }
        }

        val androidMain by getting {
            dependencies {
                api(libs.decompose.android)
            }
        }
    }
}
