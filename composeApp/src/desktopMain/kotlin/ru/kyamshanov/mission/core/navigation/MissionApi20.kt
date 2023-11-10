package ru.kyamshanov.mission.core.navigation

import com.github.scribejava.apis.google.GoogleDeviceAuthorizationJsonExtractor
import com.github.scribejava.apis.openid.OpenIdJsonTokenExtractor
import com.github.scribejava.core.builder.api.DefaultApi20
import com.github.scribejava.core.extractors.DeviceAuthorizationJsonExtractor
import com.github.scribejava.core.extractors.TokenExtractor
import com.github.scribejava.core.model.OAuth2AccessToken

class MissionApi20 constructor() : DefaultApi20() {


    override fun getAccessTokenEndpoint(): java.lang.String? {
        return "http://127.0.0.1:9000/oauth/token"
    }

    override fun getAuthorizationBaseUrl(): java.lang.String? {
        return "http://127.0.0.1:9000/oauth/authorize"
    }

    override fun getAccessTokenExtractor(): TokenExtractor<OAuth2AccessToken> {
        return OpenIdJsonTokenExtractor.instance()
    }

    override fun getRevokeTokenEndpoint(): java.lang.String? {
        return "https://oauth2.googleapis.com/revoke"
    }

    override fun getDeviceAuthorizationEndpoint(): java.lang.String? {
        return "https://oauth2.googleapis.com/device/code"
    }

    override fun getDeviceAuthorizationExtractor(): DeviceAuthorizationJsonExtractor {
        return GoogleDeviceAuthorizationJsonExtractor.instance()
    }
}
