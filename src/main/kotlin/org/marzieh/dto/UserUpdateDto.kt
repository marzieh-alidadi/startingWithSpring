package org.marzieh.dto

data class UserUpdateDto(
    val id: String? = null,
    val phone: Long? = null,
    val name: String? = null,
    val family: String? = null,
    val pass: String? = null,
    val roles: List<UserRole>? = null
)

