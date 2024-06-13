package com.teamsparta.jobtopia.domain.common.exception

import com.teamsparta.jobtopia.domain.common.exception.dto.ErrorResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(KakaoAccessTokenException::class)
    fun handleKakaoAccessTokenException(ex: KakaoAccessTokenException): ResponseEntity<ErrorResponse> {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ErrorResponse(message = ex.message))
    }

    @ExceptionHandler(KakaoUserInfoException::class)
    fun handleKakaoUserInfoException(ex: KakaoUserInfoException): ResponseEntity<ErrorResponse> {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorResponse(message = ex.message))
    }



}