package com.teamsparta.jobtopia.domain.comment.model

import com.teamsparta.jobtopia.domain.comment.dto.CommentPostDTO
import com.teamsparta.jobtopia.domain.post.model.Post
import com.teamsparta.jobtopia.domain.users.model.Users
import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDateTime

@Entity
@Table(name = "comment")
class Comment private constructor(
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    val user: Users,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    val post: Post,

    @Column(name="content", nullable = false)
    var content: String,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    @Column(name = "is_deleted", nullable = false)
    var isDeleted: Boolean = false

    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    val createdAt: LocalDateTime = LocalDateTime.now()

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = true)
    val updatedAt: LocalDateTime? = null

    fun modifyComment(content: String)
    {
        this.content = content
    }

    companion object{
        fun fromDto(commentPostDTO: CommentPostDTO, post : Post, user: Users): Comment {
            return Comment(
                user = user,
                post = post,
                content = commentPostDTO.content
            )
        }
    }
}