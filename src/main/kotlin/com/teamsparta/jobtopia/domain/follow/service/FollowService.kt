package com.teamsparta.jobtopia.domain.follow.service

import com.teamsparta.jobtopia.domain.follow.dto.FollowingRequestDto
import com.teamsparta.jobtopia.domain.follow.dto.FollowingResponseDto

interface FollowService {
    fun followUser(followingRequestDto: FollowingRequestDto, userId: Long): Boolean
    fun getFollowingUserList(userId: Long): FollowingResponseDto
}