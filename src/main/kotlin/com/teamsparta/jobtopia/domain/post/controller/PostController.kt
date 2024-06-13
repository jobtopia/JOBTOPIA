package com.teamsparta.jobtopia.domain.post.controller



import com.teamsparta.jobtopia.domain.post.dto.PostRequest
import com.teamsparta.jobtopia.domain.post.dto.PostResponse
import com.teamsparta.jobtopia.domain.post.dto.UpdatePostResponse
import com.teamsparta.jobtopia.domain.post.service.PostService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RequestMapping("/api/v1/posts")
@RestController
class PostController(
    private val postService: PostService,
) {

    @GetMapping
    fun getPostList(@PageableDefault pageable: Pageable):ResponseEntity<Page<PostResponse>>{
       return ResponseEntity.status(HttpStatus.OK).body(postService.getPostList(pageable))
    }

    @GetMapping("/{postId}")
        fun getPostById(@PathVariable postId: Long): ResponseEntity<PostResponse>{
          return ResponseEntity.status(HttpStatus.OK).body(postService.getPostById(postId))
        }

    @PostMapping
        fun createPost(@RequestBody postRequest: PostRequest): ResponseEntity<PostResponse> {
          return ResponseEntity.status(HttpStatus.CREATED).body(postService.createPost(postRequest))
    }

    @PutMapping("/{postId}")
       fun updatePost(@PathVariable postId: Long, @RequestBody postRequest:PostRequest): ResponseEntity<UpdatePostResponse> {
         return ResponseEntity.status(HttpStatus.OK).body(postService.updatePost(postId, postRequest))
    }

    @DeleteMapping("/{postId}")
      fun deletePost(@PathVariable postId: Long): ResponseEntity<Unit> {
         return ResponseEntity.status(HttpStatus.NO_CONTENT).body(postService.deletePost(postId))
    }


}