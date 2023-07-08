plugins {
    id("multiplatform-lib")
}

dependencies {
    commonMainImplementation(projects.core.navigation.impl)
    commonMainImplementation(projects.foundation.impl)
    commonMainImplementation(projects.core.base.impl)
    commonMainImplementation(projects.core.sessionFront.impl)
    commonMainImplementation(projects.core.network.impl)
    commonMainImplementation(projects.components.mainScreen.impl)
}