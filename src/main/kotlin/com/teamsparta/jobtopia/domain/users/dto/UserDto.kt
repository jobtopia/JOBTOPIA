package com.teamsparta.jobtopia.domain.users.dto

import com.teamsparta.jobtopia.domain.users.model.Users

data class UserDto(
    val id: Long,
    val userName: String,
    val nickname: String,
    val role: String,
    val description: String?
) {
    companion object {
        fun fromEntity(user: Users): UserDto {
            return UserDto(
                id = user.id!!,
                userName = user.userName,
                nickname = user.profile.nickname,
                role = user.role.name,
                description = user.profile.description
            )
        }
    }
}

