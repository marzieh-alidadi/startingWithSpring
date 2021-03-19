package org.marzieh.dto

data class UserCreateDto(
    val phone: Long,
    val name: String,
    val family: String,
    var pass: String
)