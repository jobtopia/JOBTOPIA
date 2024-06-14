package com.teamsparta.jobtopia.domain.comment.service

import com.teamsparta.jobtopia.domain.comment.dto.CommentDTO
import com.teamsparta.jobtopia.domain.comment.dto.CommentModifyDTO
import com.teamsparta.jobtopia.domain.comment.dto.CommentPostDTO
import com.teamsparta.jobtopia.domain.comment.model.Comment
import com.teamsparta.jobtopia.domain.comment.repository.CommentRepository
import com.teamsparta.jobtopia.domain.common.exception.InvalidCredentialException
import com.teamsparta.jobtopia.domain.common.exception.ModelNotFoundException
import com.teamsparta.jobtopia.domain.post.repository.PostRepository
import com.teamsparta.jobtopia.domain.users.repository.UserRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CommentServiceImpl(
    private val postRepository: PostRepository,
    private val commentRepository: CommentRepository,
    private val userRepository: UserRepository
) : CommentService {
    override fun getCommentList(postId: Long): List<CommentDTO> {
        return commentRepository.findAllByPostId(postId).map { CommentDTO.from(it) }
    }

    override fun getComment(postId: Long, commentId: Long): CommentDTO {
        val comment = getValidComment(commentId)
        isPostAndCommentAreSame(postId, comment)
        return CommentDTO.from(comment)
    }

    @Transactional
    override fun postComment(postId: Long, commentPostDTO: CommentPostDTO, userId: Long): CommentDTO {
        val post = postRepository.findByIdOrNull(postId) ?: throw ModelNotFoundException("Todo", postId)
        val user = userRepository.findByIdOrNull(userId) ?: throw ModelNotFoundException("Users", userId)
        val comment = Comment.fromDto(commentPostDTO, post, user)
        return CommentDTO.from(commentRepository.save(comment))
    }

    @Transactional
    override fun modifyComment(
        postId: Long,
        commentId: Long,
        commentModifyDTO: CommentModifyDTO,
        userId: Long
    ): CommentDTO {
        val (content) = commentModifyDTO
        val comment = getValidComment(commentId)
        isPostAndCommentAreSame(postId, comment)
        if (userId != comment.user.id) throw InvalidCredentialException("User ID does not match")
        comment.modifyComment(content)
        return CommentDTO.from(commentRepository.save(comment))
    }

    @Transactional
    override fun deleteComment(postId: Long, commentId: Long, userId: Long) {
        val comment = getValidComment(commentId)
        isPostAndCommentAreSame(postId, comment)
        if (userId != comment.user.id) throw InvalidCredentialException("User ID does not match")
        commentRepository.delete(comment)
    }

    private fun isPostAndCommentAreSame(postId: Long, comment: Comment) {
        if (postId != comment.post.id)
            throw IllegalStateException("$postId and ${comment.post.id} is not matched")
    }

    private fun getValidComment(commentId: Long): Comment {
        return commentRepository.findByIdOrNull(commentId) ?: throw ModelNotFoundException("Comment", commentId)
    }

    fun isOwner(commentId: Long, userId: Long): Boolean {
        val comment = commentRepository.findByIdOrNull(commentId) ?: throw ModelNotFoundException("Comment", commentId)
        return comment.user.id == userId
    }


}