package com.firestartermc.profile.extension

import org.bson.Document

fun Document.flatten(): MutableMap<String, Any> {
    return mutableMapOf<String, Any>().also { this.flatten(it) }
}

private fun Document.flatten(data: MutableMap<String, Any>, prefix: String = "") {
    for (entry in this) {
        val fullKey = if (prefix.isNotEmpty()) "$prefix.${entry.key}" else entry.key

        if (entry.value == null) {
            continue
        }

        if (entry.value is Document) {
            this.flatten(data, fullKey)
            continue
        }

        data[fullKey] = entry.value
    }
}