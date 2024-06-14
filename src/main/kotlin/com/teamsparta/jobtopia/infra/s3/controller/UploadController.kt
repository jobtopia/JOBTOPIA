package com.teamsparta.jobtopia.infra.s3.controller



import com.teamsparta.jobtopia.infra.s3.dto.ImageUploadResponse
import com.teamsparta.jobtopia.infra.s3.service.S3Service
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile


@RestController
@RequestMapping("/api/images")
class S3Controller(
    private val s3Service: S3Service
) {

    @PostMapping(consumes = [MediaType.MULTIPART_FORM_DATA_VALUE], produces =
    [MediaType.APPLICATION_XML_VALUE])
    fun uploadImage(
        @RequestParam("file") file: MultipartFile,
    ): ResponseEntity<ImageUploadResponse> {
        return ResponseEntity
            .ok(ImageUploadResponse(url=s3Service.upload(file)))
    }
}


