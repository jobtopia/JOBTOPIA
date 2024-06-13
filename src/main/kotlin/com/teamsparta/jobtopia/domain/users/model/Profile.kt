package com.teamsparta.jobtopia.domain.users.model

import jakarta.persistence.Column
import jakarta.persistence.Embeddable

@Embeddable
data class Profile(
    @Column(name = "nickname", length = 255, unique = true, nullable = false)
    var nickname: String,

    @Column(name = "description")
    var description: String,
)

