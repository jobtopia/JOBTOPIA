package com.teamsparta.jobtopia.domain.post.service

import com.teamsparta.jobtopia.domain.post.dto.PostRequest
import com.teamsparta.jobtopia.domain.post.dto.PostResponse
import com.teamsparta.jobtopia.domain.post.dto.UpdatePostResponse
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.security.core.Authentication


interface PostService {

    fun getPostList(pageable: Pageable): Page<PostResponse>
    fun getPostById(postId: Long):PostResponse
    fun createPost(postRequest: PostRequest,authentication: Authentication):PostResponse
    fun updatePost(postId: Long,postRequest: PostRequest,authentication: Authentication):UpdatePostResponse
    fun deletePost(postId: Long,authentication: Authentication)
}