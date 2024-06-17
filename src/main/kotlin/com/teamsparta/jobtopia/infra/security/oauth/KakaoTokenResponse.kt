package com.teamsparta.jobtopia.infra.security.oauth

import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.databind.annotation.JsonNaming


@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy::class)
data class KakaoTokenResponse (
    val accessToken: String,
)
