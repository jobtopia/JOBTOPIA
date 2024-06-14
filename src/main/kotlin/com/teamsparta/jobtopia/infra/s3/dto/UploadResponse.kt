package com.teamsparta.jobtopia.infra.s3.dto

data class UploadResponse(
    val fileName: String,
    val url: String
)
