import org.gradle.kotlin.dsl.getValue
import org.gradle.kotlin.dsl.getting

plugins {
    id("kotlin-api")
    id("network")
}

dependencies {
    commonMainApi(projects.core.oauth2.api)
    commonMainApi(projects.core.di.common)
    commonMainImplementation(projects.core.network.api)
}