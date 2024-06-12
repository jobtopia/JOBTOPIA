package com.teamsparta.jobtopia.domain.users.dto

import com.teamsparta.jobtopia.domain.users.model.UserRole
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size

data class SignUpRequest(
    @field:NotBlank
    @field:Size(min = 4, max = 10, message = "Username must be between 4 and 10 characters")
    @field:Pattern(regexp = "^[a-z0-9]+$", message = "Username can only contain lowercase letters and digits")
    val userName: String,

    @field:NotBlank
    @field:Size(min = 8, max = 15, message = "Password must be between 8 and 15 characters")
    @field:Pattern(
        regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,15}$",
        message = "Password must contain at least one uppercase letter, one lowercase letter, one number, and one special character"
    )
    val password: String,

    @field:NotBlank
    @field:Size(min = 2, max = 20, message = "Nickname must be between 2 and 20 characters")
    val nickname: String,

    @field:NotBlank
    @field:Size(max = 50, message = "Description must be 50 characters or less")
    val description: String?,

    val role: UserRole
)