package com.teamsparta.jobtopia.post.repository

import com.teamsparta.jobtopia.post.model.Post
import org.springframework.data.jpa.repository.JpaRepository


interface  PostRepository: JpaRepository<Post, Long> {

}
