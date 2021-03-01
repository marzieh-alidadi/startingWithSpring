package org.marzieh.service

import org.marzieh.document.to
import org.marzieh.dto.UserCreateDto
import org.marzieh.dto.UserDto
import org.marzieh.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UserService @Autowired constructor(
    private val userRepository: UserRepository
) {
    fun save(t: UserCreateDto): UserDto {
        return userRepository.save(t.to()).toDto()
    }

    fun get(id: String): UserDto? {
        val user = userRepository.findById(id)
        return if (user.isPresent) {
            user.get().toDto()
        } else {
            null
        }
    }
}