plugins {
    id("multiplatform-compose")
}

dependencies {
    commonMainImplementation(projects.core.navigation.api)
}