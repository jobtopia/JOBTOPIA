package com.teamsparta.jobtopia.domain.follow.model

import jakarta.persistence.Embeddable
import java.io.Serializable

@Embeddable
class FollowId(
    val user: Long = 0,
    val followingUserId: Long = 0,
): Serializable
