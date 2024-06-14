package com.teamsparta.jobtopia.domain.follow.controller

import com.teamsparta.jobtopia.domain.follow.dto.FollowingRequestDto
import com.teamsparta.jobtopia.domain.follow.dto.FollowingResponseDto
import com.teamsparta.jobtopia.domain.follow.service.FollowService
import com.teamsparta.jobtopia.infra.security.UserPrincipal
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/users/follows")
class FollowController(
    private val followService: FollowService
) {

    @PostMapping
    fun followUser(
        @AuthenticationPrincipal principal: UserPrincipal,
        @RequestBody followingRequestDto: FollowingRequestDto
    ): ResponseEntity<Boolean> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(followService.followUser(followingRequestDto, principal.id))
    }

    @GetMapping
    fun getFollowingUserList(
        @AuthenticationPrincipal principal: UserPrincipal,
    ): ResponseEntity<FollowingResponseDto> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(followService.getFollowingUserList(principal.id))
    }
}
