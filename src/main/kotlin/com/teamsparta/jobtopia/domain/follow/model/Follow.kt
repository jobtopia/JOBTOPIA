package com.teamsparta.jobtopia.domain.follow.model

import com.teamsparta.jobtopia.domain.users.model.Users
import jakarta.persistence.*

@IdClass(FollowId::class)
@Entity
@Table(name = "follow")
class Follow(
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    val user: Users,

    @Id
    @Column(name = "following_user_id", nullable = false)
    val followingUserId: Long,
) {
    companion object {
        fun from(followingUserId: Long, user: Users): Follow {
            return Follow(
                followingUserId = followingUserId,
                user = user
            )
        }
    }
}
