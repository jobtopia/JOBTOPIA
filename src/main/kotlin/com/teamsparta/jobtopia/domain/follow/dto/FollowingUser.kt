package com.teamsparta.jobtopia.domain.follow.dto

import com.teamsparta.jobtopia.domain.users.model.Users

data class FollowingUser(
    var followingUserId: Long,
    val followingUserName: String,
    val followingUserNickName : String
){
    companion object{
        fun from(user: Users): FollowingUser{
            return FollowingUser(
                followingUserId = user.id!!,
                followingUserName = user.userName,
                followingUserNickName = user.profile.nickname
            )
        }
    }
}