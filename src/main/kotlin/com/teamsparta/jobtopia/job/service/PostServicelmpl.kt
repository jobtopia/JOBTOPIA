package com.teamsparta.jobtopia.post.service

import com.teamsparta.jobtopia.post.dto.PostRequest
import com.teamsparta.jobtopia.post.dto.PostResponse
import com.teamsparta.jobtopia.post.model.Post
import com.teamsparta.jobtopia.post.model.toResponse
import com.teamsparta.jobtopia.post.repository.PostRepository
import jakarta.persistence.EntityNotFoundException
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class PostServicelmpl(
    private val postRepository: PostRepository,
):PostService{
    override fun getPostList(pageable: Pageable): Page<PostResponse> {
       return postRepository.findAll(pageable).map { it.toResponse() }
    }

    override fun getPostById(postId: Long): PostResponse {
        val post = postRepository.findByIdOrNull(postId) ?: throw EntityNotFoundException()
        return post.toResponse()
    }

    override fun createPost(postRequest: PostRequest): PostResponse {
        return postRepository.save(
            Post(
                title = postRequest.title,
                content =  postRequest.content
            )
        ).toResponse()
    }

    override fun updatePost(postId: Long,postRequest: PostRequest): PostResponse {
        val post = postRepository.findByIdOrNull(postId) ?: throw EntityNotFoundException()
        val (title,content) = postRequest

        post.title = title
        post.content = content
        post.updatedAt = LocalDateTime.now()
        return postRepository.save(post).toResponse()

    }

    override fun deletePost(postId: Long) {
        val post = postRepository.findByIdOrNull(postId) ?: throw EntityNotFoundException()
        postRepository.delete(post)
    }


}
