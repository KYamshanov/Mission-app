import org.gradle.kotlin.dsl.getValue
import org.gradle.kotlin.dsl.getting

plugins {
    id("kotlin-api")
    id("network")
}

dependencies {
    commonMainApi(projects.core.sessionFront.api)
    commonMainApi(projects.core.di.common)
    commonMainImplementation(projects.core.network.api)
    commonMainImplementation(projects.core.base.api)
    commonMainImplementation(projects.core.oauth2.api)
}