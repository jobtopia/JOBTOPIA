package com.teamsparta.jobtopia.post.dto

import java.time.LocalDateTime

data class PostResponse (
    val id : Long,
    val title : String,
    val content : String,
    val createdAt : LocalDateTime,
    val updatedAt  : LocalDateTime,
)