package com.teamsparta.jobtopia.domain.post.service

import com.teamsparta.jobtopia.domain.common.exception.ModelNotFoundException
import com.teamsparta.jobtopia.domain.post.dto.PostRequest
import com.teamsparta.jobtopia.domain.post.dto.PostResponse
import com.teamsparta.jobtopia.domain.post.dto.UpdatePostResponse
import com.teamsparta.jobtopia.domain.post.model.Post
import com.teamsparta.jobtopia.domain.post.model.toResponse
import com.teamsparta.jobtopia.domain.post.model.toUpdatePostResponse
import com.teamsparta.jobtopia.domain.post.repository.PostRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
class PostServiceImpl(
    private val postRepository: PostRepository,
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
    override fun createPost(postRequest: PostRequest): PostResponse {
        return postRepository.save(
            Post(
                title = postRequest.title,
                content = postRequest.content
            )
        ).toResponse()
    }


    @Transactional
    override fun updatePost(postId: Long, postRequest: PostRequest): UpdatePostResponse {
        val post = postRepository.findByIdOrNull(postId) ?: throw ModelNotFoundException("삭제된 게시글입니다.", postId)
        if (post.isDeleted) throw ModelNotFoundException("삭제된 게시글입니다.", postId)

        val (title, content) = postRequest
        post.title = title
        post.content = content
        post.updatedAt = LocalDateTime.now()
        return postRepository.save(post).toUpdatePostResponse()
    }


    @Transactional
    override fun deletePost(postId: Long) {
        val post = postRepository.findByIdOrNull(postId) ?: throw ModelNotFoundException("없는 게시글입니다.", postId)
        if (post.isDeleted) throw ModelNotFoundException("이미 삭제된 게시글 ", postId)
        post.softDeleted()
        post.deletedAt = LocalDateTime.now()

    }


}
