plugins {
    id("multiplatform-compose")
    use(libs.plugins.moko.resources)
}

android {
    namespace = "ru.kyamshanov.mission.core.ui"
}


dependencies {
    //base api
    commonMainApi(projects.core.theme)
    commonMainApi("dev.icerock.moko:resources:0.23.0")
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


multiplatformResources {
    multiplatformResourcesPackage = "ru.kyamshanov.mission.core.ui"
    multiplatformResourcesClassName = "Res"
}