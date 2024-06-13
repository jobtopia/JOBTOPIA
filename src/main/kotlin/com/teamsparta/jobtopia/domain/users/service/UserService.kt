package com.teamsparta.jobtopia.domain.users.service

import com.teamsparta.jobtopia.domain.users.dto.*

interface UserService {
    fun signUp(request: SignUpRequest): UserDto
    fun login(loginRequest: LoginRequest): LoginResponse
    fun logout(token: String)
    fun updateProfile(profile: UserUpdateProfileDto, userId: Long): UserDto
}