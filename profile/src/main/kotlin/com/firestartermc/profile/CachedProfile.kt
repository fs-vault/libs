package com.firestartermc.profile

import java.util.*

class CachedProfile(private val data: MutableMap<String, Any>) : Profile {

    override val id: UUID = get("_id") ?: throw IllegalStateException("Invalid profile id.")
    override val username: String = get("username") ?: "???"
    override val createdAt: Long = get("created_at") ?: System.currentTimeMillis()

    override fun <T> get(key: String): T? {
        return data[key]?.let { it as T }
    }

    override fun <T> get(key: String, fallback: T): T {
        return get(key) ?: fallback
    }

    override fun set(key: String, value: Any?) {
        value?.let { data[key] = it } ?: data.remove(key)
    }
}