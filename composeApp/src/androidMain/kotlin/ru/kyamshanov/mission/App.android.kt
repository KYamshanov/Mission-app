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
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import ru.kyamshanov.mission.core.di.common.DiRegistry
import ru.kyamshanov.mission.core.splash_screen.MainContent

class AndroidApp : Application() {
    companion object {

        lateinit var INSTANCE: AndroidApp
    }

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
        Napier.base(DebugAntilog())
        DiRegistry.registering()
    }
}

class AppActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            window.statusBarColor = Color.Companion.Transparent.toArgb()
            MainContent()
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
