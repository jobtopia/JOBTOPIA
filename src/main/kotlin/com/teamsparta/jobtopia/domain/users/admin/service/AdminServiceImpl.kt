package com.teamsparta.jobtopia.domain.users.admin.service

import com.teamsparta.jobtopia.domain.comment.dto.CommentDTO
import com.teamsparta.jobtopia.domain.comment.dto.CommentModifyDTO
import com.teamsparta.jobtopia.domain.comment.repository.CommentRepository
import com.teamsparta.jobtopia.domain.common.exception.ModelNotFoundException
import com.teamsparta.jobtopia.domain.post.dto.GetPostResponse
import com.teamsparta.jobtopia.domain.post.dto.PostRequest
import com.teamsparta.jobtopia.domain.post.repository.PostRepository
import com.teamsparta.jobtopia.domain.reaction.dto.ReactionResponse
import com.teamsparta.jobtopia.domain.reaction.service.ReactionService
import com.teamsparta.jobtopia.infra.s3.service.S3Service
import com.teamsparta.jobtopia.infra.security.UserPrincipal
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile
import java.time.LocalDateTime

@Service
class AdminServiceImpl(
    private val postRepository: PostRepository,
    private val commentRepository: CommentRepository,
    private val s3Service: S3Service,
    private val reactionService: ReactionService

    ):AdminService{
    override fun getPostsByAdminId(pageable: Pageable): Page<GetPostResponse> {
        val post = postRepository.findAll(pageable)

        val postIds = post.map{ it.id!! }.toMutableList()

        val reactionList = mutableListOf<ReactionResponse>()

        postIds.forEach { id ->
            val reaction = reactionService.getCountReactions(id, null)
            reactionList.add(reaction)
        }

        val postToReaction = postIds.zip(reactionList).toMap()

        return post.map {
            val reaction = postToReaction[it.id]
            GetPostResponse.from(it, reaction!!, it.comments.map { comment -> CommentDTO.from(comment, reactionService.getCountReactions(null, comment.id)) })
        }


    }

    @Transactional
    override fun updatePostsByAdminId(postId: Long, postRequest: PostRequest, files: MultipartFile?): GetPostResponse {

        val post = postRepository.findByIdOrNull(postId)
            ?: throw ModelNotFoundException("삭제된 게시글입니다.", postId)


        if (post.isDeleted) throw ModelNotFoundException("삭제된 게시글입니다.", postId)

        val imageUrl = files?.let { s3Service.upload(it) }

        post.files?.let { s3Service.delete(it.split("m/")[1]) }

        post.createPostRequest(postRequest, imageUrl)
        val reaction = reactionService.getCountReactions(post.id!!, null)
        return GetPostResponse.from(postRepository.save(post), reaction)
    }

    @Transactional
    override fun deletePostsByAdminId(postId: Long) {

        val post = postRepository.findByIdOrNull(postId)
            ?: throw ModelNotFoundException("삭제된 게시글입니다.", postId)

        if (post.isDeleted) throw ModelNotFoundException("삭제된 게시글입니다.", postId)

        post.softDeleted()
        reactionService.deleteReaction(post, null)
        post.files?.let { s3Service.delete(it.split("m/")[1]) }

        post.deletedAt = LocalDateTime.now()
    }

    @Transactional
    override fun updateCommentsByAdminId(
        postId: Long,
        commentId: Long,
        commentModifyDTO: CommentModifyDTO
    ): CommentDTO {

        val (content) = commentModifyDTO
        val comment = commentRepository.findByIdOrNull(commentId)
            ?: throw ModelNotFoundException("Comment", commentId)

        if (postId != comment.post.id)
            throw IllegalStateException("$postId and ${comment.post.id} is not matched")

        comment.modifyComment(content)

        val reaction = reactionService.getCountReactions(null, commentId)
        return CommentDTO.from(commentRepository.save(comment), reaction)
    }

    @Transactional
    override fun deleteCommentsByAdminId(postId: Long, commentId: Long) {
        val comment = commentRepository.findByIdOrNull(commentId)
            ?: throw ModelNotFoundException("Comment", commentId)

        if (postId != comment.post.id)
            throw IllegalStateException("$postId and ${comment.post.id} is not matched")


        commentRepository.delete(comment)
        reactionService.deleteReaction(null, comment)
    }

}