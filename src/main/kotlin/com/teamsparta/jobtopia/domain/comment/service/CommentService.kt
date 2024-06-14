package com.teamsparta.jobtopia.domain.comment.service

import com.teamsparta.jobtopia.domain.comment.dto.CommentDTO
import com.teamsparta.jobtopia.domain.comment.dto.CommentModifyDTO
import com.teamsparta.jobtopia.domain.comment.dto.CommentPostDTO

interface CommentService {
    fun getCommentList(postId: Long): List<CommentDTO>

    fun getComment(postId: Long, commentId: Long): CommentDTO

    fun postComment(postId: Long, commentPostDTO: CommentPostDTO, userId: Long): CommentDTO

    fun modifyComment(postId: Long, commentId: Long, commentModifyDTO: CommentModifyDTO, userId: Long): CommentDTO

    fun deleteComment(postId: Long, commentId: Long, userId: Long)

    fun commentLikeReaction(postId: Long, commentId: Long, userId: Long)

    fun commentDisLikeReaction(postId: Long, commentId: Long, userId: Long)
}