package com.teamsparta.jobtopia.domain.users.model

import jakarta.persistence.*

@Entity
@Table(name = "app_user")
class Users(
    @Column(name = "username", nullable = false)
    val userName: String,

    @Column(name = "password")
    val password: String,

    @Embedded
    var profile: Profile,

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    val role: UserRole,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
}
