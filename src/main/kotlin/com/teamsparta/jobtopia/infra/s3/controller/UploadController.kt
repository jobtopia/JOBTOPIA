package com.teamsparta.jobtopia.infra.s3.controller


import com.teamsparta.jobtopia.infra.s3.service.S3Service
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile


@RestController
@RequestMapping("/upload")
class UploadController(
    private val s3Service: S3Service
){
    @PostMapping
     fun profileUpload(@RequestParam("file") file: MultipartFile) {
         //TODO DTO 추가? service 추가?
     }
}


