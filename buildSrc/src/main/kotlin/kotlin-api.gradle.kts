import org.gradle.accessors.dm.LibrariesForLibs

//see https://github.com/gradle/gradle/issues/15383
val libs = the<LibrariesForLibs>()

plugins {
    id("org.jetbrains.kotlin.multiplatform")
}


kotlin {
    jvm()

    js(IR) {
        browser()
        useCommonJs()
    }

}

dependencies {
    commonMainImplementation(libs.kotlinx.coroutines.core)
    commonMainImplementation(libs.napier)
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions.jvmTarget = Versions.TargetJVM
}