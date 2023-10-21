package ru.kyamshanov.mission

import android.app.Application
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.ComposeView
import androidx.core.view.WindowCompat
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.arkivanov.decompose.defaultComponentContext
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import ru.kyamshanov.mission.core.di.bundle.DiRegistry
import ru.kyamshanov.mission.core.di.impl.Di
import ru.kyamshanov.mission.core.navigation.api.di.NavigationComponent
import ru.kyamshanov.mission.core.navigation.common.ComposableScreen
import ru.kyamshanov.mission.core.navigation.impl.DefaultRootComponent
import ru.kyamshanov.mission.core.navigation.impl.di.NavigationComponentImpl
import ru.kyamshanov.mission.core.platform_base.di.PlatformBaseComponentBuilder
import ru.kyamshanov.mission.foundation.api.splash_screen.di.SplashScreenComponent
import ru.kyamshanov.mission.session_front.api.di.SessionFrontComponent
import ru.kyamshanov.mission.session_front.api.session.UnidentifiedSession

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

class AppActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        window.statusBarColor = Color.Companion.Transparent.toArgb()
        setContentView(R.layout.activity_main)

        val defaultRootComponent = DefaultRootComponent(
            initialScreen = requireNotNull(Di.getComponent<SplashScreenComponent>()).splashScreen,
            componentContext = defaultComponentContext()
        )

        requireNotNull(Di.getInternalComponent<NavigationComponent, NavigationComponentImpl>())
            .navigatorControllerHolder.rootComponent = defaultRootComponent

        defaultRootComponent.childStack.subscribe {
            val fragmentTransaction = supportFragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.fragmentContainer, ComposableFragment())
            fragmentTransaction.commit()
        }

        val content: View = findViewById(android.R.id.content)
        content.viewTreeObserver.addOnPreDrawListener(
            object : ViewTreeObserver.OnPreDrawListener {
                override fun onPreDraw(): Boolean {
                    val v =
                        requireNotNull(Di.getComponent<SessionFrontComponent>()).sessionInfo.session
                    return if (v == UnidentifiedSession) false
                    else {
                        content.viewTreeObserver.removeOnPreDrawListener(this)
                        true
                    }
                }
            }
        )
    }
}

class ComposableFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                val rootComponent = requireNotNull(
                    Di.getInternalComponent<NavigationComponent, NavigationComponentImpl>()?.navigatorControllerHolder?.rootComponent
                ) { "NavigationComponent needs for navigation " }

                MainContent(rootComponent)
            }
        }
    }
}
