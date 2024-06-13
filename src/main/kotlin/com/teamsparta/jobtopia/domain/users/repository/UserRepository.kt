package com.teamsparta.jobtopia.domain.users.repository

import com.teamsparta.jobtopia.domain.users.model.Users
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<Users, Long> {
    fun findByUserName(userName: String) : Users?
}