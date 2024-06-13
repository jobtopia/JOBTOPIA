package com.teamsparta.jobtopia.domain.common.util.password

import com.teamsparta.jobtopia.domain.users.model.Users
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component

@Component
class PasswordHistory(
    private val passwordEncoder: PasswordEncoder,
) {
    fun isPasswordInHistory(user: Users, newPassword: String): Boolean {
        return user.passwordHistory.any { passwordEncoder.matches(newPassword, it) }
    }

    fun updatePasswordHistory(user: Users, newPassword: String) {
        if (user.passwordHistory.size >= 3) {
            user.passwordHistory.removeAt(0)
        }
        user.passwordHistory.add(newPassword)
    }
}