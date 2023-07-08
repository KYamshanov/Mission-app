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
    commonMainApi(libs.orbit.mvi.core)
}