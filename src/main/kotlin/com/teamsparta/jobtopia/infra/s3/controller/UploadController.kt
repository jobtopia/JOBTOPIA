package com.teamsparta.jobtopia.infra.s3.controller




import com.teamsparta.jobtopia.infra.s3.service.S3Service

import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile


@RequestMapping("/api/v1")
@RestController

class UploadController(
    private val s3Service: S3Service
) {

    @PostMapping("/upload")
    fun fileUpload(@RequestParam("image") multipartFile: MultipartFile): String {
        return s3Service.upload(multipartFile)
    }
}

