package com.teamsparta.jobtopia.domain.comment.repository

import com.teamsparta.jobtopia.domain.comment.model.Comment
import org.springframework.data.jpa.repository.JpaRepository

interface CommentRepository: JpaRepository<Comment, Long> {
    fun findAllByPostId(postId: Long): List<Comment>
}