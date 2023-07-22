plugins {
    id("kotlin-api")
}

dependencies {
    commonMainImplementation(projects.core.navigation.api)
}