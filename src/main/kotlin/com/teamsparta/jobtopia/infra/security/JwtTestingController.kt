package com.teamsparta.jobtopia.infra.security


import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jws
import org.springframework.web.bind.annotation.*

@RequestMapping("/api/v1/token")
@RestController
class JwtTestingController {

    @GetMapping
    fun getJwtToken(@RequestParam("userName") userName: String): String {
        val jwtPlugin = JwtPlugin()
        return jwtPlugin.generateAccessToken("username", userName)
    }

    @GetMapping("valid")
    fun validJwtToken(@RequestParam("token") token: String): Result<Jws<Claims>> {
        val jwtPlugin = JwtPlugin()
        val validateToken = jwtPlugin.validateToken(token)
        return validateToken
    }

}