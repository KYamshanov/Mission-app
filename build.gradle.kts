plugins {
    use(libs.plugins.multiplatform).apply(false)
    use(libs.plugins.compose).apply(false)
    use(libs.plugins.android.application).apply(false)

    alias(libs.plugins.kotlinx.serialization).apply(false)
}
