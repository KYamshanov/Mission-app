rootProject.name = "Mission-app"

pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
}

//For enable access to modules as projects.core.ui
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

include(":js-test")


include(":composeApp")
include(
    ":core:theme",
    ":core:ui",
    ":core:login-screen:api",
    ":core:login-screen:impl",
    ":core:di:api",
    ":core:di:common",
    ":core:di:bundle",
    ":core:navigation:api",
    ":core:navigation:impl",
    ":core:navigation:common",
    ":core:network:api",
    ":core:network:impl",
    ":core:session-front:api",
    ":core:session-front:impl",
    ":core:session-front:common",
    ":core:base:api",
    ":core:base:impl",
    ":core:platform-base",
)
include(
    ":foundation:api",
    ":foundation:impl",
)
include(
    ":components:main-screen:api",
    ":components:main-screen:impl",
)