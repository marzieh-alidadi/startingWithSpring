package org.marzieh.service

import com.sun.org.apache.xerces.internal.impl.xpath.regex.REUtil.matches
import nonapi.io.github.classgraph.json.Id
import org.marzieh.document.to
import org.marzieh.dto.UserCreateDto
import org.marzieh.dto.UserDto
import org.marzieh.dto.UserUpdateDto
import org.marzieh.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.util.regex.Pattern.matches

@Service
class UserService @Autowired constructor(
    private val userRepository: UserRepository
) {
    fun getById(id: String): UserDto? {
        return userRepository.findByIdOrNull(id)?.toDto()
    }
    fun getProfileById(id: String): UserDto? {
        return userRepository.findByIdOrNull(id)?.toDto()
    }

    fun save(t: UserCreateDto): UserDto {
        return userRepository.save(t.to()).toDto()
    }

    fun getByPhone(phone: Long): UserDto? {
        return userRepository.findByPhone(phone)?.toDto()
    }
    
    
    
    
    
    
    
}
