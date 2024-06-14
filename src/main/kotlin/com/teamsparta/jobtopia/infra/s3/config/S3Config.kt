package com.teamsparta.jobtopia.infra.s3.config

import com.amazonaws.auth.AWSCredentials
import com.amazonaws.auth.AWSStaticCredentialsProvider

import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.AmazonS3ClientBuilder
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource

@PropertySource("classpath:aws.yml")
@Configuration
class S3Config(
    @Value("\${accessKey}") private val accessKey : String,
    @Value("\${secretKey}") private val secretKey : String,
    @Value("\${region}") private val region : String
) {

    @Bean
    fun amazonS3Client(): AmazonS3 {
        val credentials : AWSCredentials = BasicAWSCredentials(accessKey, secretKey)
        return AmazonS3ClientBuilder.standard()
            .withRegion(region)
            .withCredentials(AWSStaticCredentialsProvider(credentials))
            .build()
    }
}