package org.example.project.dao

object TokenHolder {
    var accessToken: String? = null
    var refreshToken: String? = null

    fun clear() {
        accessToken = null
        refreshToken = null
    }
}