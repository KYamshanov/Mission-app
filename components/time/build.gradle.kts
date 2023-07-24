plugins {
    id("kotlin-api")
}



kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(libs.kotlinx.datetime)
                implementation(projects.core.di.common)
            }
        }
    }
}