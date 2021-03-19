package org.marzieh.dto

import org.marzieh.document.UserDocument

data class ProfileResponse(
    val id: String,
    val phone: Long,
    val name: String,
    val family: String,
    val roles: List<UserRole>
)

fun UserDto.toPR(): ProfileResponse {
    return ProfileResponse(id, phone, name, family, listOf(UserRole.USER))
}