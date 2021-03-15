package org.marzieh.dto

import org.marzieh.dto.UserRole

data class UserCreateDto(
    val phone: Long,
    val name: String,
    val family: String,
    var pass: String
)