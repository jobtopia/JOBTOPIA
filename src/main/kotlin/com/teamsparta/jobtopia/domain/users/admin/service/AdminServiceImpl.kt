package com.teamsparta.jobtopia.domain.users.admin.service

import com.teamsparta.jobtopia.domain.comment.dto.CommentDTO
import com.teamsparta.jobtopia.domain.comment.dto.CommentModifyDTO
import com.teamsparta.jobtopia.domain.comment.repository.CommentRepository
import com.teamsparta.jobtopia.domain.common.exception.ModelNotFoundException
import com.teamsparta.jobtopia.domain.post.dto.GetPostResponse
import com.teamsparta.jobtopia.domain.post.dto.PostRequest
import com.teamsparta.jobtopia.domain.post.repository.PostRepository
import com.teamsparta.jobtopia.domain.reaction.service.ReactionService
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime


@Service
class AdminServiceImpl(
    private val postRepository: PostRepository,
    private val reactionService: ReactionService,
    private val commentRepository: CommentRepository,

    ): AdminService{
    @Transactional
    override fun  updatePostsByAdminID(postId: Long, postRequest: PostRequest): GetPostResponse {


        val post = postRepository.findByIdOrNull(postId)
            ?: throw ModelNotFoundException("삭제된 게시글입니다.", postId)

        if (post.isDeleted) throw ModelNotFoundException("삭제된 게시글입니다.", postId)

        post.createPostRequest(postRequest)
        val reaction = reactionService.getCountReactions(post.id!!, null)
        return GetPostResponse.from(postRepository.save(post), reaction)
    }

    override fun deletePostsByAdminID(postId: Long) {


        val post = postRepository.findByIdOrNull(postId)
            ?: throw ModelNotFoundException("삭제된 게시글입니다.", postId)


        if (post.isDeleted) throw ModelNotFoundException("삭제된 게시글입니다.", postId)

        post.softDeleted()
        reactionService.deleteReaction(post, null)

        post.deletedAt = LocalDateTime.now()
    }

    override fun updateCommentByAdminId(postId: Long, commentId: Long,commentModifyDTO: CommentModifyDTO) : CommentDTO {


        val (content) = commentModifyDTO

        val comment = commentRepository.findByIdOrNull(commentId)
            ?: throw ModelNotFoundException("Comment", commentId)

        if (postId != comment.post.id)
            throw IllegalStateException("$postId and ${comment.post.id} is not matched")

        comment.modifyComment(content)
        val reaction = reactionService.getCountReactions(null, commentId)
        return CommentDTO.from(commentRepository.save(comment), reaction)
    }

    override fun deleteCommentByAdminId(postId: Long, commentId: Long) {


        val comment = commentRepository.findByIdOrNull(commentId)
            ?: throw ModelNotFoundException("Comment", commentId)

        if (postId != comment.post.id)
            throw IllegalStateException("$postId and ${comment.post.id} is not matched")

        commentRepository.delete(comment)
        reactionService.deleteReaction(null, comment)
    }
}