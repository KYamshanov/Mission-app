import org.gradle.accessors.dm.LibrariesForLibs

//see https://github.com/gradle/gradle/issues/15383
val libs = the<LibrariesForLibs>()

plugins {
    id("multiplatform-compose")
    id("dev.icerock.mobile.multiplatform-resources")
    id("multiplatform-network")
}

kotlin {
    sourceSets {

        val commonMain by getting {
            dependencies {
                implementation(project(":core:ui"))
                implementation(project(":core:di:common"))
                implementation(project(":core:navigation:api"))
                implementation(project(":core:navigation:common"))
                implementation(libs.moko.resources.core)
            }
        }

        val commonUiMain by getting {
            dependencies {
                implementation(libs.moko.resources.compose)
            }
        }

        val jsMain by getting {
            dependencies {
                implementation(libs.moko.resources.compose)
            }
        }
    }
}

multiplatformResources {
    multiplatformResourcesPackage =
        "ru.kyamshanov.mission" + project.path.toString().replace(":", ".").replace("-", "_")
    multiplatformResourcesClassName = "Res"
}