package com.teamsparta.jobtopia.domain.users.controller

import com.teamsparta.jobtopia.domain.users.dto.SignUpRequest
import com.teamsparta.jobtopia.domain.users.dto.UserDto
import com.teamsparta.jobtopia.domain.users.service.UserService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/api/v1")
@RestController
class UserController(
    private val userService: UserService
) {
    @PostMapping("/signup")
    fun signUp(
        @Valid @RequestBody signUpRequest: SignUpRequest
    ): ResponseEntity<UserDto> {
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(userService.signUp(signUpRequest))
    }

}