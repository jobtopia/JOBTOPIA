package com.teamsparta.jobtopia.domain.follow.dto

import com.teamsparta.jobtopia.domain.users.model.Users

data class FollowingResponseDto(
    val userId: Long,
    val followingUserList: List<FollowingUser>
) {
    companion object {
        fun from(userId: Long, userList: List<Users>): FollowingResponseDto {
            return FollowingResponseDto(
                userId = userId,
                followingUserList = userList.map { FollowingUser.from(it) }
            )
        }
    }
}
