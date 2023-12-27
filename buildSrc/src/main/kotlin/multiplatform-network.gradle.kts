import org.gradle.accessors.dm.LibrariesForLibs
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getValue
import org.gradle.kotlin.dsl.getting

//see https://github.com/gradle/gradle/issues/15383
val libs = the<LibrariesForLibs>()

plugins {
    id("multiplatform-base")
    id("org.jetbrains.kotlin.plugin.serialization")
}


kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(libs.ktor.serialization.json)
            }
        }
    }
}