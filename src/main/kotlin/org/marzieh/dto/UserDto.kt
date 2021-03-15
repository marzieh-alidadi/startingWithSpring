package org.marzieh.dto

data class UserDto(
    val id: String,
    val phone: Long,
    val name: String,
    val family: String,
    val pass: String,
    val roles: List<UserRole>
)