package com.teamsparta.jobtopia.domain.post.dto

import com.teamsparta.jobtopia.domain.comment.dto.CommentDTO
import com.teamsparta.jobtopia.domain.post.model.Post
import com.teamsparta.jobtopia.domain.reaction.dto.ReactionResponse
import java.time.LocalDateTime

data class GetPostResponse(
    val id : Long,
    val title : String,
    val content : String,
    val file : String?,
    val createdAt : LocalDateTime,
    val updatedAt  : LocalDateTime?,
    val isDeleted : Boolean,
    var like : Int = 0,
    var dislike : Int = 0,
    val comments: List<CommentDTO> = emptyList()
) {
    companion object {
        fun from(post: Post, reaction: ReactionResponse): GetPostResponse {
            return GetPostResponse(
                post.id!!,
                post.title,
                post.content,
                post.files,
                post.createdAt,
                post.updatedAt,
                post.isDeleted,
                reaction.like,
                reaction.dislike
            )
        }
        fun from(post: Post): GetPostResponse {
            return GetPostResponse(
                post.id!!,
                post.title,
                post.content,
                post.files,
                post.createdAt,
                post.updatedAt,
                post.isDeleted,
            )
        }
        fun from(post: Post, reaction: ReactionResponse, comments: List<CommentDTO>): GetPostResponse {
            return GetPostResponse(
                post.id!!,
                post.title,
                post.content,
                post.files,
                post.createdAt,
                post.updatedAt,
                post.isDeleted,
                reaction.like,
                reaction.dislike,
                comments
            )
        }
    }
}
