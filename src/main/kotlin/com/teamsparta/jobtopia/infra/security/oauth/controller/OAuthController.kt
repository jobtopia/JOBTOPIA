package com.teamsparta.jobtopia.infra.security.oauth.controller

import com.teamsparta.jobtopia.infra.security.oauth.service.OAuthService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController


@RestController
class OAuthController (
    private val oAuthService: OAuthService
) {


    @GetMapping("/oauth/kakao")
    fun getLoginPage(){
        oAuthService.getLoginPage()

    }
}