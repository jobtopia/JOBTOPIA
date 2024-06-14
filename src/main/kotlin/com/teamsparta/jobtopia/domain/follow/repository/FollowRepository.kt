package com.teamsparta.jobtopia.domain.follow.repository

import com.teamsparta.jobtopia.domain.follow.model.Follow
import org.springframework.data.jpa.repository.JpaRepository

interface FollowRepository : JpaRepository<Follow, Long> {
    fun findByFollowingUserId(followingUserId: Long): Follow?
    fun findAllByUserId(userId: Long): List<Follow>
}