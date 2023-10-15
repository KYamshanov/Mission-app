plugins {
    id("multiplatform-ui-lib")
}

dependencies {

    desktopMainImplementation(project(mapOf("path" to ":core:navigation:api")))

    //android oauth
    androidMainImplementation("net.openid:appauth:0.11.1")
    androidMainImplementation("androidx.activity:activity-compose:1.7.2")

    //base api
    commonMainApi(projects.foundation.api)

    //depends api-modules
    commonMainImplementation(projects.core.sessionFront.api)
    commonMainImplementation(projects.components.mainScreen.api)

    //depends Common modules
    commonMainImplementation(projects.core.navigation.common)
    commonMainImplementation(projects.core.di.common)
    commonMainImplementation(projects.core.theme)
    commonMainImplementation(projects.core.authorization.api)
}