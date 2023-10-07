rootProject.name = "Mission-app"

pluginManagement {
    repositories {
        mavenLocal()
        google()
        gradlePluginPortal()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
}

dependencyResolutionManagement {
    repositories {
        mavenLocal()
        google()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
}

//For enable access to modules as projects.core.ui
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

include(":composeApp", ":missionAppDesktop", ":missionAppJs")
include(
    ":core:theme",
    ":core:ui",
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
    ":core:base:api",
    ":core:base:impl",
    ":core:platform-base",
    ":core:oauth2:api",
    ":core:oauth2:impl",
    ":core:authorization:api",
    ":core:authorization:desktop",
)
include(
    ":foundation:api",
    ":foundation:impl",
)
include(
    ":components:main-screen:api",
    ":components:main-screen:impl",
    ":components:project:api",
    ":components:project:impl",
    ":components:time",
    ":components:point:api",
    ":components:point:impl",
)