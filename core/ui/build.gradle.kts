plugins{
    id("multiplatform-compose")
}

android {
    namespace = "ru.kyamshanov.mission.core.ui"
}

kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {
                api(projects.core.theme)
            }
        }
    }
}