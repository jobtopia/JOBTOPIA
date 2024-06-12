package com.teamsparta.jobtopia.post.service

import com.teamsparta.jobtopia.post.dto.PostRequest
import com.teamsparta.jobtopia.post.dto.PostResponse
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable


interface PostService {

    fun getPostList(pageable: Pageable): Page<PostResponse>
    fun getPostById(postId: Long):PostResponse
    fun createPost(postRequest: PostRequest):PostResponse
    fun updatePost(postId: Long,postRequest: PostRequest):PostResponse
    fun deletePost(postId: Long)
}