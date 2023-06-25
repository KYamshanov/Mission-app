plugins {
    id("multiplatform-lib")
}


kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {
                api(projects.core.base.api)
                implementation(projects.core.di.impl)
            }
        }
    }
}
