package ru.kyamshanov.mission

import android.app.Application
import android.os.Bundle
import android.view.View
import android.view.ViewTreeObserver
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.core.view.WindowCompat
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import ru.kyamshanov.mission.core.di.bundle.DiRegistry
import ru.kyamshanov.mission.core.di.impl.Di
import ru.kyamshanov.mission.core.navigation.impl.MainContent
import ru.kyamshanov.mission.core.platform_base.di.PlatformBaseComponentBuilder
import ru.kyamshanov.mission.foundation.api.splash_screen.di.SplashScreenComponent

class AndroidApp : Application() {
    companion object {

        lateinit var INSTANCE: AndroidApp
    }

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
        Napier.base(DebugAntilog())
        Di.registration(PlatformBaseComponentBuilder(this))
        DiRegistry.registering()
    }
}

class AppActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        val lifecycle = LifecycleRegistry()
        val componentContext = DefaultComponentContext(lifecycle)
        val splashScreen =
            requireNotNull(Di.getComponent<SplashScreenComponent>()).composableSplashScreen

        setContent {
            window.statusBarColor = Color.Companion.Transparent.toArgb()
            MainContent(componentContext, splashScreen)
        }

        val content: View = findViewById(android.R.id.content)
        content.viewTreeObserver.addOnPreDrawListener(
            object : ViewTreeObserver.OnPreDrawListener {
                override fun onPreDraw(): Boolean {
                    content.viewTreeObserver.removeOnPreDrawListener(this)
                    return false
                }
            }
        )
    }
}
