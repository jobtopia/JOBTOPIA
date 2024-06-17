package com.teamsparta.jobtopia.domain.comment.dto

import com.teamsparta.jobtopia.domain.comment.model.Comment
import com.teamsparta.jobtopia.domain.reaction.dto.ReactionResponse

data class CommentDTO(
    val id: Long?,
    val content: String,
    var like: Int = 0,
    var dislike: Int = 0
) {
    companion object {
        fun from(comment: Comment, reaction: ReactionResponse): CommentDTO {
            return CommentDTO(
                id = comment.id,
                content = comment.content,
                like = reaction.like,
                dislike = reaction.dislike
            )
        }
        fun from(comment: Comment): CommentDTO {
            return CommentDTO(
                id = comment.id,
                content = comment.content,
            )
        }
    }
}
