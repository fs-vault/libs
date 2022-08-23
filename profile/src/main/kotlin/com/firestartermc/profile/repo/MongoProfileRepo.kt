package com.firestartermc.profile.repo

import com.firestartermc.profile.Profile
import com.google.common.cache.CacheBuilder
import com.mongodb.client.MongoCollection
import com.mongodb.client.model.Filters
import com.mongodb.client.model.Updates
import org.bson.Document
import org.slf4j.Logger
import java.util.*
import java.util.concurrent.TimeUnit

class MongoProfileRepo(private val logger: Logger, val collection: MongoCollection<Document>) : ProfileRepo {

    private val cache = CacheBuilder.newBuilder()
        .expireAfterWrite(15, TimeUnit.SECONDS)
        .build(ProfileLoader(this))

    override suspend fun create(id: UUID): Profile {
        collection.insertOne(Document("_id", id).append("created_at", System.currentTimeMillis()))
        return fetch(id) ?: throw IllegalStateException("Newly created profile cannot be accessed.")
    }

    override suspend fun fetch(id: UUID): Profile? {
        return runCatching { cache[id] }.getOrNull()
    }

    override suspend fun update(id: UUID, key: String, value: Any?) {
        val update = when (value) {
            null -> Updates.unset(key)
            else -> Updates.set(key, value)
        }

        try {
            logger.debug("Updating profile {}: {}", id, update)
            collection.updateOne(Filters.eq("_id", id), update)
        } catch (exception: Exception) {
            logger.warn("Failed update for profile {}: {}", id, update, exception)
        }
    }
}