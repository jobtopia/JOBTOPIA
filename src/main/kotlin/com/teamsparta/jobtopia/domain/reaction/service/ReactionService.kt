package com.teamsparta.jobtopia.domain.reaction.service

import com.teamsparta.jobtopia.domain.comment.model.Comment
import com.teamsparta.jobtopia.domain.post.model.Post
import com.teamsparta.jobtopia.domain.reaction.dto.ReactionResponse
import com.teamsparta.jobtopia.domain.reaction.model.ReactionType

interface ReactionService {
    fun updateReaction(userId: Long, post: Post?, comment: Comment?, reactionType: ReactionType)
    fun deleteReaction(post: Post?, comment: Comment?)
    fun getCountReactions(postId: Long?, commentId: Long?): ReactionResponse
}