package com.teamsparta.jobtopia.domain.comment.service

import com.teamsparta.jobtopia.domain.comment.dto.CommentDTO
import com.teamsparta.jobtopia.domain.comment.dto.CommentModifyDTO
import com.teamsparta.jobtopia.domain.comment.dto.CommentPostDTO
import com.teamsparta.jobtopia.domain.comment.model.Comment
import com.teamsparta.jobtopia.domain.comment.repository.CommentRepository
import com.teamsparta.jobtopia.domain.common.exception.InvalidCredentialException
import com.teamsparta.jobtopia.domain.common.exception.ModelNotFoundException
import com.teamsparta.jobtopia.domain.post.dto.GetPostResponse
import com.teamsparta.jobtopia.domain.post.repository.PostRepository
import com.teamsparta.jobtopia.domain.reaction.dto.ReactionResponse
import com.teamsparta.jobtopia.domain.reaction.model.ReactionType
import com.teamsparta.jobtopia.domain.reaction.repository.ReactionRepository
import com.teamsparta.jobtopia.domain.reaction.service.ReactionService
import com.teamsparta.jobtopia.domain.users.repository.UserRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CommentServiceImpl(
    private val postRepository: PostRepository,
    private val commentRepository: CommentRepository,
    private val userRepository: UserRepository,
    private val reactionService : ReactionService
) : CommentService {
    override fun getCommentList(postId: Long): List<CommentDTO> {
        val comment = commentRepository.findAllByPostId(postId)

        val commentIds = comment.map{ it.id!! }.toMutableList()
        val reactionList = mutableListOf<ReactionResponse>()

        commentIds.forEach { id ->
            val reaction = reactionService.getCountReactions(null, id)
            reactionList.add(reaction)
        }

        val postToReaction = commentIds.zip(reactionList).toMap()

        return comment.map {
            val reaction = postToReaction[it.id]
            CommentDTO.from(it, reaction!!)
        }
    }

    override fun getComment(postId: Long, commentId: Long): CommentDTO {
        val comment = getValidComment(commentId)
        isPostAndCommentAreSame(postId, comment)
        val reaction = reactionService.getCountReactions(null, commentId)
        return CommentDTO.from(comment, reaction)
    }

    @Transactional
    override fun postComment(postId: Long, commentPostDTO: CommentPostDTO, userId: Long): CommentDTO {
        val post = postRepository.findByIdOrNull(postId) ?: throw ModelNotFoundException("Todo", postId)
        val user = userRepository.findByIdOrNull(userId) ?: throw ModelNotFoundException("Users", userId)
        val comment = Comment.fromDto(commentPostDTO, post, user)

        val reaction = reactionService.getCountReactions(null, comment.id)
        return CommentDTO.from(commentRepository.save(comment), reaction)
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

        val reaction = reactionService.getCountReactions(null, commentId)
        return CommentDTO.from(commentRepository.save(comment), reaction)
    }

    @Transactional
    override fun deleteComment(postId: Long, commentId: Long, userId: Long) {
        val comment = getValidComment(commentId)
        isPostAndCommentAreSame(postId, comment)
        if (userId != comment.user.id) throw InvalidCredentialException("User ID does not match")

        commentRepository.delete(comment)
        reactionService.deleteReaction(null, comment)
    }

    override fun commentLikeReaction(postId: Long, commentId: Long, userId: Long) {
        val comment = getValidComment(commentId)
        isPostAndCommentAreSame(postId, comment)
        reactionService.updateReaction(userId, null, comment, ReactionType.LIKE)
    }

    override fun commentDisLikeReaction(postId: Long, commentId: Long, userId: Long) {
        val comment = getValidComment(commentId)
        isPostAndCommentAreSame(postId, comment)
        reactionService.updateReaction(userId, null, comment, ReactionType.DISLIKE)
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