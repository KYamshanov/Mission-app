import org.gradle.accessors.dm.LibrariesForLibs

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
                implementation(libs.decompose.core)
            }
        }

        val commonUiMain by creating {
            dependsOn(commonMain)

            dependencies {
                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.material)
                @OptIn(org.jetbrains.compose.ExperimentalComposeLibrary::class)
                implementation(compose.components.resources)

                implementation(libs.decompose.compose)

            }
        }

        val androidMain by getting {
            dependsOn(commonUiMain)
            dependencies {
                implementation(libs.decompose.android)
            }
        }

        val desktopMain by getting {
            dependsOn(commonUiMain)
        }

        val jsMain by getting {
            dependencies {
                implementation(compose.html.core)
                implementation(compose.runtime)
            }
        }
    }
}