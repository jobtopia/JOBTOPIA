package com.teamsparta.jobtopia.domain.users.model

import com.teamsparta.jobtopia.domain.users.dto.UserUpdateProfileDto
import jakarta.persistence.*

@Entity
@Table(name = "app_user")
class Users(
    @Column(name = "username", nullable = false)
    val userName: String,

    @Column(name = "password")
    var password: String,

    @Embedded
    var profile: Profile,

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    val role: UserRole = UserRole.USER,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    fun updateProfile(profileDto: UserUpdateProfileDto, password: String) {
        this.password = password
        this.profile.nickname = profileDto.nickname
        this.profile.description = profileDto.description
    }
}
