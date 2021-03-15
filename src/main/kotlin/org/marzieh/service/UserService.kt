package org.marzieh.service

import nonapi.io.github.classgraph.json.Id
import org.marzieh.document.to
import org.marzieh.dto.UserCreateDto
import org.marzieh.dto.UserDto
import org.marzieh.dto.UserUpdateDto
import org.marzieh.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class UserService @Autowired constructor(
    private val userRepository: UserRepository
) {
    /*
    fun get(id: String): UserDto? {
        val user = userRepository.findById(id)
        return if (user.isPresent) {
            user.get().toDto()
        } else {
            null
        }
    }
*/

    fun getById(id: String): UserDto? {
        return userRepository.findByIdOrNull(id)?.toDto()
    }

    

    fun delete(id: String) {
        userRepository.deleteById(id)
    }


    fun save(t: UserCreateDto): UserDto {
        return userRepository.save(t.to()).toDto()
    }

    fun getAll(): List<UserDto>? {
        return userRepository.findAll().map { it.toDto() }
    }

    fun update(id: String, t: UserUpdateDto): UserDto? {
        val currentUserDocument = userRepository.findByIdOrNull(id) ?: return null
        return userRepository.save(t.to(currentUserDocument)).toDto()
    }

    fun getByPhone(phone: Long): UserDto? {
        return userRepository.findByPhone(phone)?.toDto()
    }
    
    
    
    
    
    
    
}
