package com.teamsparta.jobtopia.domain.post.service

import com.teamsparta.jobtopia.domain.common.exception.ModelNotFoundException
import com.teamsparta.jobtopia.domain.post.dto.PostRequest
import com.teamsparta.jobtopia.domain.post.dto.PostResponse
import com.teamsparta.jobtopia.domain.post.dto.UpdatePostResponse
import com.teamsparta.jobtopia.domain.post.model.Post
import com.teamsparta.jobtopia.domain.post.model.toResponse
import com.teamsparta.jobtopia.domain.post.model.toUpdatePostResponse
import com.teamsparta.jobtopia.domain.post.repository.PostRepository
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
): PostService {

    override fun getPostList(pageable: Pageable): Page<PostResponse> {
        return postRepository. findByisDeletedFalse(pageable).map { it.toResponse() }
    }


    override fun getPostById(postId: Long): PostResponse {
        val post = postRepository.findByIdOrNull(postId) ?: throw ModelNotFoundException("삭제된 게시글입니다.", postId)
        if (post.isDeleted) throw ModelNotFoundException("삭제된 게시글입니다.", postId)

        return post.toResponse()
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
    override fun updatePost(postId: Long, postRequest: PostRequest,authentication: Authentication): UpdatePostResponse {
        val userPrincipal = authentication.principal as UserPrincipal
        val users = userRepository.findByIdOrNull(userPrincipal.id) ?: throw ModelNotFoundException("없는 user 입니다.", userPrincipal.id)
        val post = postRepository.findByIdOrNull(postId) ?: throw ModelNotFoundException("삭제된 게시글입니다.", postId)

        if(users.id != post.users.id ) throw IllegalStateException("권한이 없습니다.")

        if (post.isDeleted) throw ModelNotFoundException("삭제된 게시글입니다.", postId)

       post.createPostRequest(postRequest)
        return postRepository.save(post).toUpdatePostResponse()
    }


    @Transactional
    override fun deletePost(postId: Long,authentication: Authentication) {
        val userPrincipal = authentication.principal as UserPrincipal
        val post = postRepository.findByIdOrNull(postId) ?: throw ModelNotFoundException("삭제된 게시글입니다.", postId)
        val users = userRepository.findByIdOrNull(userPrincipal.id) ?: throw ModelNotFoundException("없는 user 입니다.", userPrincipal.id)


        if(users.id != post.users.id ) throw IllegalStateException("권한이 없습니다.")

        if (post.isDeleted) throw ModelNotFoundException("삭제된 게시글입니다.", postId)

        post.softDeleted()
        post.deletedAt = LocalDateTime.now()

    }


}
