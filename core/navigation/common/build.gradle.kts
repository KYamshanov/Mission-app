plugins{
    id("multiplatform-ui-lib")
}

kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(projects.core.navigation.api)
                api(libs.voyager)
            }
        }
    }
}