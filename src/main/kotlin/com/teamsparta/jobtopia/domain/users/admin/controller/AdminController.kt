package com.teamsparta.jobtopia.domain.users.admin.controller

import com.teamsparta.jobtopia.domain.comment.dto.CommentDTO
import com.teamsparta.jobtopia.domain.comment.dto.CommentModifyDTO
import com.teamsparta.jobtopia.domain.post.dto.GetPostResponse
import com.teamsparta.jobtopia.domain.post.dto.PostRequest
import com.teamsparta.jobtopia.domain.users.admin.service.AdminService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RequestMapping("/api/v1/admin")
@RestController
class AdminController(
    private val adminService: AdminService,
) {

    @PutMapping("/posts/{postId}")
    @PreAuthorize("hasRole('ADMIN')")
    fun updatePostByAdmin(
        @PathVariable postId: Long,
        @RequestBody postRequest: PostRequest,
    ): ResponseEntity<GetPostResponse> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(adminService.updatePostsByAdminID(postId, postRequest))
    }

    @DeleteMapping("/posts/{postId}")
    @PreAuthorize("hasRole('ADMIN')")
    fun deletePostByAdmin(
        @PathVariable postId: Long,
    ): ResponseEntity<Unit>{
        return  ResponseEntity
            .status(HttpStatus.NO_CONTENT)
            .body(adminService.deletePostsByAdminID(postId))
    }


    @PutMapping("/posts/{postId}/comments/{commentId}")
    @PreAuthorize("hasRole('ADMIN')")
    fun updateCommentByAdmin(
        @PathVariable postId: Long,
        @PathVariable commentId :Long,
        @RequestBody commentModifyDTO: CommentModifyDTO,
    ):ResponseEntity<CommentDTO>  {
       return ResponseEntity
           .status(HttpStatus.OK)
           .body(adminService.updateCommentByAdminId(postId,commentId,commentModifyDTO))
    }

    @DeleteMapping("/posts/{postId}/comments/{commentId}")
    @PreAuthorize("hasRole('ADMIN')")
    fun deleteCommentByAdmin(
        @PathVariable postId: Long,
        @PathVariable commentId: Long,
    ):ResponseEntity<Unit> {
        return ResponseEntity
            .status(HttpStatus.NO_CONTENT)
            .body(adminService.deleteCommentByAdminId(postId,commentId))
    }


}