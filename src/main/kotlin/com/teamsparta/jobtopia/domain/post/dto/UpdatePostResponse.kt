package com.teamsparta.jobtopia.domain.post.dto


import java.time.LocalDateTime

data class UpdatePostResponse(
    val id : Long,
    val title : String,
    val content : String,
    val createdAt : LocalDateTime,
    val updatedAt  : LocalDateTime,
    val deleted : Boolean,
)