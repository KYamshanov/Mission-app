package ru.kyamshanov.mission.oauth2

import io.ktor.utils.io.core.toByteArray
import java.security.MessageDigest
import java.security.SecureRandom
import java.util.Base64


internal actual fun generateCodeVerifier(): String {
    val secureRandom = SecureRandom()
    val codeVerifier = ByteArray(32)
    secureRandom.nextBytes(codeVerifier)
    return Base64.getUrlEncoder().withoutPadding().encodeToString(codeVerifier)
}

internal actual fun generateCodeChallange(codeVerifier: String): String {
    val bytes: ByteArray = codeVerifier.toByteArray(Charsets.US_ASCII)
    val messageDigest = MessageDigest.getInstance("SHA-256")
    messageDigest.update(bytes, 0, bytes.size)
    val digest = messageDigest.digest()
    return Base64.getUrlEncoder().withoutPadding().encodeToString(digest)
}