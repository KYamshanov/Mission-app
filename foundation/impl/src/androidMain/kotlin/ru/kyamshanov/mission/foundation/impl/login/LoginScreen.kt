package ru.kyamshanov.mission.foundation.impl.login

import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.foundation.layout.Box
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import net.openid.appauth.AuthorizationService

@Composable
internal fun LoginComposable() =
    Box(contentAlignment = Alignment.Center) {

        val result = remember { mutableStateOf<Intent?>(null) }
        val launcher =
            rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                result.value = it.data
            }

        val authRepository = AuthRepository()
        val authService: AuthorizationService = AuthorizationService(LocalContext.current)


        Button(onClick = {

            val customTabsIntent = CustomTabsIntent.Builder().build()

            val authRequest = authRepository.getAuthRequest()

            println("1. Generated verifier=${authRequest.codeVerifier},challenge=${authRequest.codeVerifierChallenge}")

            val openAuthPageIntent = authService.getAuthorizationRequestIntent(
                authRequest,
                customTabsIntent
            )

            launcher.launch(openAuthPageIntent)
        }) {
            Text(text = "Войти")
        }

    }