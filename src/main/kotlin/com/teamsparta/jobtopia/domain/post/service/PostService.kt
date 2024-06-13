package com.teamsparta.jobtopia.domain.post.service

import com.teamsparta.jobtopia.domain.post.dto.GetPostResponse
import com.teamsparta.jobtopia.domain.post.dto.PostRequest
import com.teamsparta.jobtopia.domain.post.dto.PostResponse
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.security.core.Authentication


interface PostService {

    fun getPostList(pageable: Pageable): Page<GetPostResponse>
    fun getPostById(postId: Long):GetPostResponse
    fun createPost(postRequest: PostRequest,authentication: Authentication):PostResponse
    fun updatePost(postId: Long,postRequest: PostRequest,authentication: Authentication):GetPostResponse
    fun deletePost(postId: Long,authentication: Authentication)
    fun postLikeReaction(postId: Long, userId: Long)
    fun postDisLikeReaction(postId: Long, userId: Long)
}