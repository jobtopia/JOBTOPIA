package com.teamsparta.jobtopia.domain.users.admin.service

import com.teamsparta.jobtopia.domain.comment.dto.CommentDTO
import com.teamsparta.jobtopia.domain.comment.dto.CommentModifyDTO
import com.teamsparta.jobtopia.domain.post.dto.GetPostResponse
import com.teamsparta.jobtopia.domain.post.dto.PostRequest
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.web.multipart.MultipartFile


interface AdminService {

    fun getPostsByAdminId(pageable: Pageable) : Page<GetPostResponse>
    fun updatePostsByAdminId(postId:Long, postRequest: PostRequest, image: MultipartFile?) :GetPostResponse
    fun deletePostsByAdminId(postId:Long)
    fun updateCommentsByAdminId(postId: Long,commentId:Long,commentModifyDTO: CommentModifyDTO) :CommentDTO
    fun deleteCommentsByAdminId(postId: Long,commentId:Long)



}