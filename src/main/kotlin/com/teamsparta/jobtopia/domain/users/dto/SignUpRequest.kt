package com.teamsparta.jobtopia.domain.users.dto

import com.teamsparta.jobtopia.domain.users.model.UserRole
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class SignUpRequest(
    val userName : String,
    val password : String,
    @field:NotBlank
    @field:Size(min = 2, max = 20, message = "Nickname must be between 2 and 20 characters")
    val nickname: String,

    @field:NotBlank
    @field:Size(max = 50, message = "Description must be 50 characters or less")
    val description: String?,
    val role: UserRole
)
