import org.gradle.accessors.dm.LibrariesForLibs

//see https://github.com/gradle/gradle/issues/15383
val libs = the<LibrariesForLibs>()

plugins {
    id("multiplatform-compose")
    id("dev.icerock.mobile.multiplatform-resources")
}

kotlin {
    sourceSets {

        val commonMain by getting {
            dependencies {
                implementation(project(":core:ui"))
                implementation(project(":core:di:common"))
                implementation(project(":core:navigation:api"))
                implementation(project(":core:navigation:common"))
                implementation("dev.icerock.moko:resources:0.23.0")
            }
        }

        val commonUiMain by getting {
            dependencies {
                implementation("dev.icerock.moko:resources-compose:0.23.0")
            }
        }

        val jsMain by getting {
            dependencies {
                implementation("dev.icerock.moko:resources-compose:0.23.0")
            }
        }
    }
}

multiplatformResources {
    multiplatformResourcesPackage =
        "ru.kyamshanov.mission" + project.path.toString().replace(":", ".").replace("-", "_")
    multiplatformResourcesClassName = "Res"
}