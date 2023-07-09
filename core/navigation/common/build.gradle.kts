plugins {
    id("multiplatform-compose")
}

dependencies {
    commonMainImplementation(projects.core.navigation.api)
}

kotlin {
    sourceSets {

        val androidMain by getting {
            dependencies {
                implementation(libs.androidx.compose.viewmodel)
                implementation(libs.decompose.android)
            }
        }
    }
}