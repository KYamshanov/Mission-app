import org.gradle.accessors.dm.LibrariesForLibs

//see https://github.com/gradle/gradle/issues/15383
val libs = the<LibrariesForLibs>()

plugins {
    id("kotlin-api")
    id("org.jetbrains.kotlin.plugin.serialization")
}

dependencies {
    commonMainImplementation(libs.ktor.serialization.json)
}