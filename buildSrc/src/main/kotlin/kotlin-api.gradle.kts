import org.gradle.accessors.dm.LibrariesForLibs

//see https://github.com/gradle/gradle/issues/15383
val libs = the<LibrariesForLibs>()

plugins {
    id("kotlin")
}

dependencies {
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.napier)
}