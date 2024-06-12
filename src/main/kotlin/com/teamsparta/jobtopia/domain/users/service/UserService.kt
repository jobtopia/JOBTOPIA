package com.teamsparta.jobtopia.domain.users.service

import com.teamsparta.jobtopia.domain.users.dto.LoginRequest
import com.teamsparta.jobtopia.domain.users.dto.LoginResponse
import com.teamsparta.jobtopia.domain.users.dto.SignUpRequest
import com.teamsparta.jobtopia.domain.users.dto.UserDto

interface UserService {
    fun signUp(request: SignUpRequest): UserDto
    fun login(loginRequest: LoginRequest): LoginResponse
    fun logout(token: String)
}