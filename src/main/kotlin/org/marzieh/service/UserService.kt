package org.marzieh.service

import org.marzieh.document.to
import org.marzieh.dto.UserCreateDto
import org.marzieh.dto.UserDto
import org.marzieh.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class UserService @Autowired constructor(
    private val userRepository: UserRepository
) {
    fun getById(id: String): UserDto? {
        return userRepository.findByIdOrNull(id)?.toDto()
    }

    fun save(t: UserCreateDto): UserDto {
        return userRepository.save(t.to()).toDto()
    }

    fun getByPhone(phone: Long): UserDto? {
        return userRepository.findByPhone(phone)?.toDto()
    }

}
