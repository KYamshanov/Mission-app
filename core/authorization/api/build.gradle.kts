plugins {
    id("kotlin-api")
}

dependencies {
    commonMainImplementation(projects.core.di.api)
    commonMainImplementation(projects.core.navigation.api)
    commonMainImplementation(projects.core.navigation.common)
}