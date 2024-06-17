package com.teamsparta.jobtopia.domain.users.admin.service

import com.teamsparta.jobtopia.domain.comment.dto.CommentDTO
import com.teamsparta.jobtopia.domain.comment.dto.CommentModifyDTO
import com.teamsparta.jobtopia.domain.post.dto.GetPostResponse
import com.teamsparta.jobtopia.domain.post.dto.PostRequest



interface AdminService {
    fun updatePostsByAdminID(postId:Long,postRequest: PostRequest) : GetPostResponse
    fun deletePostsByAdminID(postId:Long)
    fun updateCommentByAdminId(postId:Long,commentId:Long,commentModifyDTO: CommentModifyDTO) : CommentDTO
    fun deleteCommentByAdminId(postId: Long,commentId:Long)

}