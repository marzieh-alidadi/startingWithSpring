package org.marzieh.document

import org.marzieh.dto.UserCreateDto
import org.marzieh.dto.UserDto
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document("item")
class UserDocument(
    @Id
    var id: String?,
    val name: String,
    val link: String
) {
    fun toDto() = UserDto(
        id!!,
        name,
        link
    )
}

fun UserCreateDto.to(): UserDocument {
    return UserDocument(null, name, link)
}