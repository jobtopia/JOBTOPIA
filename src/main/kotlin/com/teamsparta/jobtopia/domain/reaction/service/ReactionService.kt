package com.teamsparta.jobtopia.domain.reaction.service

import com.teamsparta.jobtopia.domain.post.model.Post
import com.teamsparta.jobtopia.domain.reaction.dto.ReactionResponse
import com.teamsparta.jobtopia.domain.reaction.model.ReactionType

interface ReactionService {
    fun updatePostReaction(userId: Long, post: Post, reactionType: ReactionType)
    fun deletePostReaction(post: Post)
    fun getCountPostReactions(postId: Long): ReactionResponse
}