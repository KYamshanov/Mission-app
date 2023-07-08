plugins {
    id("multiplatform-ui-lib")
}

dependencies {
    //base api
    commonMainApi(projects.foundation.api)

    //depends api-modules
    commonMainImplementation(projects.core.sessionFront.api)
    commonMainImplementation(projects.components.mainScreen.api)

    //depends Common modules
    commonMainImplementation(projects.core.navigation.common)
    commonMainImplementation(projects.core.di.common)
    commonMainImplementation(projects.core.theme)
}