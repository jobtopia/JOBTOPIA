package com.teamsparta.jobtopia.infra.security.oauth.service

import com.teamsparta.jobtopia.infra.security.oauth.KakaoOAuthClient
import org.springframework.stereotype.Service


@Service
class OAuthService(
    private val kakaoOAuthClient: KakaoOAuthClient
) {
    fun getLoginPage() {
        kakaoOAuthClient.getLoginPageUrl()
    }

    fun getAccessToken(code: String) {
        kakaoOAuthClient.getAccessToken(code)
    }
}