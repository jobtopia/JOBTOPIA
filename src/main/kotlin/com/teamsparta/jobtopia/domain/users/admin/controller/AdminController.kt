package com.teamsparta.jobtopia.domain.users.admin.controller

import com.teamsparta.jobtopia.domain.comment.dto.CommentDTO
import com.teamsparta.jobtopia.domain.comment.dto.CommentModifyDTO
import com.teamsparta.jobtopia.domain.post.dto.GetPostResponse
import com.teamsparta.jobtopia.domain.post.dto.PostRequest
import com.teamsparta.jobtopia.domain.users.admin.service.AdminService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RequestMapping("/api/v1/admin")
@RestController
class AdminController(
    private val adminService: AdminService,
) {

    @GetMapping("/posts")
    @PreAuthorize("hasRole('ADMIN')")
    fun getPostsByAdmin(
        pageable: Pageable
    ): ResponseEntity<Page<GetPostResponse>> {
       return ResponseEntity.status(HttpStatus.OK).body(adminService.getPostsByAdminId(pageable))
    }

    @PutMapping("/posts/{postId}", consumes = ["multipart/form-data"])
    @PreAuthorize("hasRole('ADMIN')")
    fun updatePostsByAdmin(
        @PathVariable postId: Long,
        @RequestParam("title") title: String,
        @RequestParam("content") content: String,
        @RequestPart("file", required = false) file: MultipartFile?
    ): ResponseEntity<GetPostResponse> {
        val postRequest = PostRequest(title, content)
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(adminService.updatePostsByAdminId(postId, postRequest, file))
    }

    @DeleteMapping("/posts/{postId}")
    @PreAuthorize("hasRole('ADMIN')")
    fun deletePostsByAdmin(@PathVariable postId: Long):ResponseEntity<Unit> {
        return ResponseEntity
            .status(HttpStatus.NO_CONTENT)
            .body(adminService.deletePostsByAdminId(postId))
    }


    @PutMapping("/posts/{postId}/comments/{commentId}")
    @PreAuthorize("hasRole('ADMIN')")
    fun updateCommentsByAdmin(
        @PathVariable postId: Long,
        @PathVariable commentId: Long,
        @RequestBody commentModifyDTO: CommentModifyDTO
    ):ResponseEntity<CommentDTO>{
       return ResponseEntity
           .status(HttpStatus.OK)
           .body(adminService.updateCommentsByAdminId(postId, commentId, commentModifyDTO))
    }


    @DeleteMapping("/posts/{postId}/comments/{commentId}")
    @PreAuthorize("hasRole('ADMIN')")
    fun deleteCommentsByAdmin(
        @PathVariable postId: Long,
        @PathVariable commentId: Long
    ):ResponseEntity<Unit> {
        return  ResponseEntity
            .status(HttpStatus.NO_CONTENT)
            .body(adminService.deleteCommentsByAdminId(postId, commentId))
    }



}