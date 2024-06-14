package com.teamsparta.jobtopia.infra.s3.dto

import org.springframework.web.multipart.MultipartFile

data class FileUploadRequest(
    val file: MultipartFile,
    val objectKey: String
)
