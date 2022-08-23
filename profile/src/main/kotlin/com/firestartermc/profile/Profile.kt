package com.firestartermc.profile

import java.util.UUID

interface Profile {

    val id: UUID
    val username: String
    val createdAt: Long

    operator fun <T> get(key: String): T?
    operator fun <T> get(key: String, fallback: T): T
    operator fun set(key: String, value: Any?)
}