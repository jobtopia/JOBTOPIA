package com.teamsparta.jobtopia.domain.post.repository

import com.teamsparta.jobtopia.domain.post.model.Post

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param


interface  PostRepository: JpaRepository<Post, Long> {
    fun findByIsDeletedFalse(pageable: Pageable): Page<Post>

    @Query("SELECT p FROM Post p where p.users.id in (:userIds)")
    fun findAllByManyUserId(@Param("userIds")userId: List<Long>, pageable: Pageable): Page<Post>
}
