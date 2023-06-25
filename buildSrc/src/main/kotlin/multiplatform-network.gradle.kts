import gradle.kotlin.dsl.accessors._d4133f3c36cd1fd4a38fc6d50db50cdf.kotlin
import gradle.kotlin.dsl.accessors._d4133f3c36cd1fd4a38fc6d50db50cdf.sourceSets
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