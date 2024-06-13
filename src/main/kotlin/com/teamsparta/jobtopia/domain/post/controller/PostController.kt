package com.teamsparta.jobtopia.domain.post.controller

import com.teamsparta.jobtopia.domain.post.dto.GetPostResponse
import com.teamsparta.jobtopia.domain.post.dto.PostRequest
import com.teamsparta.jobtopia.domain.post.dto.PostResponse
import com.teamsparta.jobtopia.domain.post.service.PostService
import com.teamsparta.jobtopia.infra.security.UserPrincipal
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*

@RequestMapping("/api/v1/posts")
@RestController
class PostController(
    private val postService: PostService,
) {

    @GetMapping
    fun getPostList(
        @PageableDefault pageable: Pageable
    ):ResponseEntity<Page<GetPostResponse>>{
       return ResponseEntity
           .status(HttpStatus.OK)
           .body(postService.getPostList(pageable))
    }

    @GetMapping("/{postId}")
        fun getPostById(
        @PathVariable postId: Long
        ): ResponseEntity<GetPostResponse>{
          return ResponseEntity
              .status(HttpStatus.OK)
              .body(postService.getPostById(postId))
        }

    @PostMapping
        fun createPost(
        @RequestBody postRequest: PostRequest,
        authentication: Authentication
        ): ResponseEntity<PostResponse> {
          return ResponseEntity
              .status(HttpStatus.CREATED)
              .body(postService.createPost(postRequest, authentication))
    }

    @PutMapping("/{postId}")
       fun updatePost(
        @PathVariable postId: Long,
        @RequestBody postRequest:PostRequest,
        authentication: Authentication
       ): ResponseEntity<GetPostResponse> {
         return ResponseEntity
             .status(HttpStatus.OK)
             .body(postService.updatePost(postId, postRequest, authentication))
    }

    @DeleteMapping("/{postId}")
      fun deletePost(
        @PathVariable postId: Long,
        authentication: Authentication
      ): ResponseEntity<Unit> {
         return ResponseEntity
             .status(HttpStatus.NO_CONTENT)
             .body(postService.deletePost(postId,authentication))
    }

    @PostMapping("/{postId}/like")
    fun postLikeReaction(
        @PathVariable postId: Long,
        @AuthenticationPrincipal principal: UserPrincipal
    ): ResponseEntity<Unit> {
        postService.postLikeReaction(postId, principal.id)

        return ResponseEntity.status(HttpStatus.OK).build()
    }

    @PostMapping("/{postId}/dislike")
    fun postDisLikeReaction(
        @PathVariable postId: Long,
        @AuthenticationPrincipal principal: UserPrincipal
    ): ResponseEntity<Unit> {
        postService.postDisLikeReaction(postId, principal.id)

        return ResponseEntity.status(HttpStatus.OK).build()
    }
}