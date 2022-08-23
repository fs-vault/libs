package com.firestartermc.profile.repo

import com.firestartermc.profile.Profile
import java.util.UUID

interface ProfileRepo {

    suspend fun create(id: UUID): Profile
    suspend fun fetch(id: UUID): Profile?
    suspend fun update(id: UUID, key: String, value: Any?)
}