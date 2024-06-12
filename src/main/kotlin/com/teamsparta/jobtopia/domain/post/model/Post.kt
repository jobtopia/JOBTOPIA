package com.teamsparta.jobtopia.domain.post.model


import com.teamsparta.jobtopia.domain.post.dto.PostResponse
import com.teamsparta.jobtopia.domain.post.dto.UpdatePostResponse
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
    var updatedAt: LocalDateTime? = null,

    @Column(name ="deleted_at", nullable = false)
    var deletedAt: LocalDateTime? = null,

    @Column(name = "is_deleted", nullable = false)
    var isDeleted: Boolean = false,
){
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null


    fun softDeleted(){
        isDeleted = true
    }
}

    fun Post.toResponse(): PostResponse {
        return PostResponse(
            id = id!!,
            title = title,
            content = content,
            createdAt = createdAt,
            isDeleted = isDeleted
    )

}


    fun Post.toUpdatePostResponse(): UpdatePostResponse {
        return UpdatePostResponse(
            id = id!!,
            title = title,
            content = content,
            createdAt = createdAt,
            updatedAt = updatedAt!!,
            isDeleted = isDeleted
        )
    }
