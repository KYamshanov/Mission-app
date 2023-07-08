import org.gradle.kotlin.dsl.getValue
import org.gradle.kotlin.dsl.getting

plugins {
    id("kotlin-api")
    id("network")
}

dependencies {
    api(projects.core.sessionFront.api)
    api(projects.core.di.common)
    implementation(projects.core.network.api)
    implementation(projects.core.base.api)
}