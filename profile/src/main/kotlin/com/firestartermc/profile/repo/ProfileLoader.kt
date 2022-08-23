package com.firestartermc.profile.repo

import com.firestartermc.profile.CachedProfile
import com.firestartermc.profile.Profile
import com.firestartermc.profile.extension.flatten
import com.google.common.cache.CacheLoader
import com.mongodb.client.model.Filters.eq
import java.util.*

class ProfileLoader(private val repo: MongoProfileRepo) : CacheLoader<UUID, Profile>() {

    @Throws(Exception::class)
    override fun load(id: UUID): Profile {
        return repo.collection.find(eq(id)).first()
            ?.flatten()
            ?.let { CachedProfile(it) }
            ?: throw IllegalStateException("Profile \"$id\" does not exist.")
    }
}