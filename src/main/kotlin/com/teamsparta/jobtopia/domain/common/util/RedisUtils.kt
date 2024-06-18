package com.teamsparta.jobtopia.domain.common.util

import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Component
import java.time.Duration

@Component
class RedisUtils(
    private val redisTemplate: RedisTemplate<String, String>,
) {

    private val DURATION_TIME = 1000 * 60 * 60 * 24L * 7

    fun getData(key: String): String? {
        val valueOperations = redisTemplate.opsForValue()
        return valueOperations[key]
    }

    fun setDataExpire(key: String, value: String) {
        val valueOperations = redisTemplate.opsForValue()
        valueOperations.set(key, value, Duration.ofMillis(DURATION_TIME))
    }

    fun deleteData(key: String) {
        redisTemplate.delete(key)
    }
}