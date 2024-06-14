package com.teamsparta.jobtopia.domain.comment.dto

import com.teamsparta.jobtopia.domain.comment.model.Comment

data class CommentDTO(
    val id: Long?,
    val content: String,
) {
    companion object {
        fun from(comment: Comment): CommentDTO {
            return CommentDTO(
                id = comment.id,
                content = comment.content,
            )
        }
    }
}
