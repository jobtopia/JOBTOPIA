package com.teamsparta.jobtopia.domain.follow.service

import com.teamsparta.jobtopia.domain.common.exception.InvalidCredentialException
import com.teamsparta.jobtopia.domain.common.exception.ModelNotFoundException
import com.teamsparta.jobtopia.domain.follow.dto.FollowingRequestDto
import com.teamsparta.jobtopia.domain.follow.dto.FollowingResponseDto
import com.teamsparta.jobtopia.domain.follow.model.Follow
import com.teamsparta.jobtopia.domain.follow.repository.FollowRepository
import com.teamsparta.jobtopia.domain.users.repository.UserRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class FollowServiceImpl(
    private val userRepository: UserRepository,
    private val followRepository: FollowRepository
) : FollowService {
    override fun followUser(followingRequestDto: FollowingRequestDto, userId: Long): Boolean {
        val user = userRepository.findByIdOrNull(userId) ?: throw ModelNotFoundException("User", userId)
        val follow = followRepository.findByFollowingUserId(followingRequestDto.followingUserId)

        if (follow != null) {
            followRepository.delete(follow)
            return false
        } else {
            if (userId == followingRequestDto.followingUserId) {
                throw InvalidCredentialException("본인은 팔로우 할 수 없습니다")
            }
            val followModel = Follow.from(followingRequestDto.followingUserId, user)
            followRepository.save(followModel)
            return true
        }
    }

    override fun getFollowingUserList(userId: Long): FollowingResponseDto {
        val followList = followRepository.findAllByUserId(userId)
        val allUser = userRepository.findAllById(followList.map { it.followingUserId })
        return FollowingResponseDto.from(userId, allUser)
    }
}