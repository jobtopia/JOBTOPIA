package com.teamsparta.jobtopia.domain.users.service

import com.teamsparta.jobtopia.domain.common.exception.InvalidCredentialException
import com.teamsparta.jobtopia.domain.common.exception.ModelNotFoundException
import com.teamsparta.jobtopia.domain.common.util.password.PasswordHistory
import com.teamsparta.jobtopia.domain.users.dto.*
import com.teamsparta.jobtopia.domain.users.model.Profile
import com.teamsparta.jobtopia.domain.users.model.Users
import com.teamsparta.jobtopia.domain.users.repository.UserRepository
import com.teamsparta.jobtopia.infra.security.jwt.JwtPlugin
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserServiceImpl(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtPlugin: JwtPlugin,
    private val passwordHistory: PasswordHistory
) : UserService {
    private val tokenBlacklist = mutableSetOf<String>()

    @Transactional
    override fun signUp(request: SignUpRequest): UserDto {
        if (userRepository.findByUserName(request.userName) != null) {
            throw InvalidCredentialException("Username already exists")
        }
        val user = userRepository.save(
            Users(
                userName = request.userName,
                password = passwordEncoder.encode(request.password),
                role = request.role,
                profile = Profile(request.nickname, request.description ?: "")
            )
        )
        return UserDto.fromEntity(user)
    }

    override fun login(loginRequest: LoginRequest): LoginResponse {
        val user = userRepository.findByUserName(loginRequest.userName) ?: throw ModelNotFoundException("Users", null)
        if (user.userName != loginRequest.userName || !passwordEncoder.matches(loginRequest.password, user.password)) {
            throw InvalidCredentialException()
        }
        return LoginResponse.fromEntity(
            jwtPlugin.generateAccessToken(
                subject = user.id.toString(),
                userName = user.userName,
                role = user.role.toString()
            )
        )
    }

    override fun logout(token: String) {
        tokenBlacklist.add(token)
    }

    @Transactional
    override fun updateProfile(profile: UserUpdateProfileDto, userId: Long): UserDto {
        val user = userRepository.findByIdOrNull(userId) ?: throw ModelNotFoundException("Users", userId)
        if (profile.password != profile.confirmPassword) throw InvalidCredentialException()
        if (passwordHistory.isPasswordInHistory(user, profile.password)) throw InvalidCredentialException("최근 3번 사용한 비밀번호는 사용할 수 없습니다.")
        val password = passwordEncoder.encode(profile.password)
        user.updateProfile(profile, password)
        passwordHistory.updatePasswordHistory(user, password)
        return UserDto.fromEntity(user)
    }

    fun isTokenBlacklisted(token: String): Boolean {
        return tokenBlacklist.contains(token)
    }

}