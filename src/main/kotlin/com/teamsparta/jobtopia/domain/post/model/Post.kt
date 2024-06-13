package com.teamsparta.jobtopia.domain.post.model


import com.teamsparta.jobtopia.domain.post.dto.PostRequest
import com.teamsparta.jobtopia.domain.post.dto.PostResponse
import com.teamsparta.jobtopia.domain.users.model.Users
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    var users : Users

){
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null


    fun softDeleted(){
        isDeleted = true
        deletedAt =LocalDateTime.now()
    }

    fun createPostRequest(postRequest: PostRequest){
        title = postRequest.title
        content = postRequest.content
        updatedAt = LocalDateTime.now()
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

