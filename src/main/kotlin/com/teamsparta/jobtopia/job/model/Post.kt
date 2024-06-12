package com.teamsparta.jobtopia.post.model

import com.teamsparta.jobtopia.post.dto.PostResponse
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "post")
class Post(
    @Column(name = "title", nullable = false)
    var title : String,

    @Column(name = "content", nullable = false)
    var content : String,

    @Column(name = "created_at", nullable = false)
    var createdAt: LocalDateTime = LocalDateTime.now(),


    @Column(name = "updated_at", nullable = false)
    var updatedAt: LocalDateTime = LocalDateTime.now()
){
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
}

fun Post.toResponse():PostResponse{
    return PostResponse(
        id = id!!,
        title = title,
        content = content,
        createdAt = createdAt,
        updatedAt = updatedAt
    )


}