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

//For support build kotlin single api module for android target 1.8
tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions.jvmTarget = Versions.TargetJVM
}

//For support build java single api module for android target 1.8
tasks.withType<JavaCompile> {
    targetCompatibility = Versions.TargetJVM
}