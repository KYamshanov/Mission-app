plugins {
    id("multiplatform-compose")
}

android {
    namespace = "ru.kyamshanov.mission.core.ui"
}


dependencies {
    //base api
    commonMainApi(projects.core.theme)

    //External libs
    commonMainApi(libs.moko.mvvm)
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