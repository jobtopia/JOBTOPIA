package com.teamsparta.jobtopia.infra.security.oauth

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component


@Component
class KakaoOAuthClient(
    @Value("\${oauth2.kakao.client_id}") val clientId : String,
    @Value("\${oauth2.kakao.redirect_url}") val redirectUrl : String,
    @Value("\${oauth2.kakao.auth_server_base_url}") val authServerBaseUrl : String,
    @Value("\${oauth2.kakao.resource_server_base_url}") val resourceServerBaseUrl : String
): OAuthClient {
    override fun getLoginPageUrl(): String {
        return StringBuilder(authServerBaseUrl)
            .append("/oauth/authorize")
            .append("?client_id=").append(clientId)
            .append("&redirect_uri=").append(redirectUrl)
            .append("&response_type=").append("code")
            .toString()
    }

    override fun getAccessToken(code: String): String {
        TODO("Not yet implemented")
    }

    override fun getUserInfo(accessToken: String): OAuthUserInfoResponse {
        TODO("Not yet implemented")
    }
}