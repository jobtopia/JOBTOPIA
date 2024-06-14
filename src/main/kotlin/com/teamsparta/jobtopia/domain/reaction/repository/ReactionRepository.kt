package com.teamsparta.jobtopia.domain.reaction.repository

import com.teamsparta.jobtopia.domain.reaction.model.Reaction
import com.teamsparta.jobtopia.domain.reaction.model.ReactionType
import org.springframework.data.jpa.repository.JpaRepository

interface ReactionRepository: JpaRepository<Reaction, Long> {
    fun findByPostIdAndReaction(postId: Long, reaction: ReactionType): Reaction?
    fun deleteAllByPostId(postId: Long)
    fun countByPostIdAndReaction(postId: Long, reaction: ReactionType): Int
    fun findByCommentIdAndReaction(commentId: Long, reaction: ReactionType): Reaction?
    fun deleteAllByCommentId(commentId: Long)
    fun countByCommentIdAndReaction(commentId: Long, reaction: ReactionType): Int
}