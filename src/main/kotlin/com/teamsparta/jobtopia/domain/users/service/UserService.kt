package com.teamsparta.jobtopia.domain.users.service

import com.teamsparta.jobtopia.domain.users.dto.SignUpRequest
import com.teamsparta.jobtopia.domain.users.dto.UserDto

interface UserService {
    fun signUp(request: SignUpRequest): UserDto
}