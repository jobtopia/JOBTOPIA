package com.teamsparta.jobtopia.domain.post.service

import com.teamsparta.jobtopia.domain.common.exception.ModelNotFoundException
import com.teamsparta.jobtopia.domain.post.dto.GetPostResponse
import com.teamsparta.jobtopia.domain.post.dto.PostRequest
import com.teamsparta.jobtopia.domain.post.dto.PostResponse
import com.teamsparta.jobtopia.domain.post.model.Post
import com.teamsparta.jobtopia.domain.post.model.toResponse
import com.teamsparta.jobtopia.domain.post.repository.PostRepository
import com.teamsparta.jobtopia.domain.reaction.dto.ReactionResponse
import com.teamsparta.jobtopia.domain.reaction.model.ReactionType
import com.teamsparta.jobtopia.domain.reaction.service.ReactionService
import com.teamsparta.jobtopia.domain.users.repository.UserRepository
import com.teamsparta.jobtopia.infra.security.UserPrincipal
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
class PostServiceImpl(
    private val postRepository: PostRepository,
    private val userRepository: UserRepository,
    private val reactionService: ReactionService
): PostService {

    override fun getPostList(pageable: Pageable): Page<GetPostResponse> {
        val post = postRepository.findByIsDeletedFalse(pageable)

        val postIds = post.map{ it.id!! }.toMutableList()
        val reactionList = mutableListOf<ReactionResponse>()

        postIds.forEach { id ->
            val reaction = reactionService.getCountPostReactions(id)
            reactionList.add(reaction)
        }

        val postToReaction = postIds.zip(reactionList).toMap()

        return post.map {
            val reaction = postToReaction[it.id]
            GetPostResponse.from(it, reaction!!)
        }

    }


    override fun getPostById(postId: Long): GetPostResponse {
        val post = postRepository.findByIdOrNull(postId) ?: throw ModelNotFoundException("삭제된 게시글입니다.", postId)
        if (post.isDeleted) throw ModelNotFoundException("삭제된 게시글입니다.", postId)

        val reaction = reactionService.getCountPostReactions(postId)

        return GetPostResponse.from(post, reaction)
    }


    @Transactional
    override fun createPost(postRequest: PostRequest,authentication: Authentication): PostResponse {
        val userPrincipal = authentication.principal as UserPrincipal
        val users = userRepository.findByIdOrNull(userPrincipal.id) ?: throw ModelNotFoundException("없는 user 입니다.", userPrincipal.id)
        return postRepository.save(
            Post(
                title = postRequest.title,
                content = postRequest.content,
                users = users
            )
        ).toResponse()
    }


    @Transactional
    override fun updatePost(postId: Long, postRequest: PostRequest,authentication: Authentication): GetPostResponse {
        val userPrincipal = authentication.principal as UserPrincipal
        val users = userRepository.findByIdOrNull(userPrincipal.id) ?: throw ModelNotFoundException("없는 user 입니다.", userPrincipal.id)
        val post = postRepository.findByIdOrNull(postId) ?: throw ModelNotFoundException("삭제된 게시글입니다.", postId)

        if(users.id != post.users.id ) throw IllegalStateException("권한이 없습니다.")

        if (post.isDeleted) throw ModelNotFoundException("삭제된 게시글입니다.", postId)

        post.createPostRequest(postRequest)
        val reaction = reactionService.getCountPostReactions(post.id!!)
        return GetPostResponse.from(postRepository.save(post), reaction)
    }


    @Transactional
    override fun deletePost(postId: Long,authentication: Authentication) {
        val userPrincipal = authentication.principal as UserPrincipal
        val post = postRepository.findByIdOrNull(postId) ?: throw ModelNotFoundException("삭제된 게시글입니다.", postId)
        val users = userRepository.findByIdOrNull(userPrincipal.id) ?: throw ModelNotFoundException("없는 user 입니다.", userPrincipal.id)


        if(users.id != post.users.id ) throw IllegalStateException("권한이 없습니다.")

        if (post.isDeleted) throw ModelNotFoundException("삭제된 게시글입니다.", postId)

        post.softDeleted()
        reactionService.deletePostReaction(post)

        post.deletedAt = LocalDateTime.now()

    }

    override fun postLikeReaction(postId: Long, userId: Long) {
        val post = postRepository.findByIdOrNull(postId) ?: throw ModelNotFoundException("Post", postId)
        if (post.isDeleted) throw ModelNotFoundException("삭제된 게시글입니다.", postId)

        reactionService.updatePostReaction(userId, post, ReactionType.LIKE)
    }

    override fun postDisLikeReaction(postId: Long, userId: Long) {
        val post = postRepository.findByIdOrNull(postId) ?: throw ModelNotFoundException("Post", postId)
        if (post.isDeleted) throw ModelNotFoundException("삭제된 게시글입니다.", postId)

        reactionService.updatePostReaction(userId, post, ReactionType.DISLIKE)
    }
}
