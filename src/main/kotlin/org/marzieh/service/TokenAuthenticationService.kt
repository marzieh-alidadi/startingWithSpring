package org.marzieh.service

import com.google.gson.Gson
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.marzieh.config.ApplicationContextProvider
import org.marzieh.dto.CustomUser
import org.marzieh.dto.UserDto
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
import java.util.*
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

internal object TokenAuthenticationService {
    private var EXPIRATION: Long = 864000000
    private var SECRET = "ThisShouldBeSecret"
    private const val TOKEN_PREFIX = "Bearer"
    private const val HEADER_STRING = "Authorization"

    private val userService: UserService =
        ApplicationContextProvider().applicationContext().getBean("userService") as UserService

    fun addAuthentication(res: HttpServletResponse, principal: org.springframework.security.core.Authentication) {
        val userProfile = (principal.principal as CustomUser).profile
        val jwt = Jwts.builder()
            .setSubject(Gson().toJson(userProfile))
            .setExpiration(Date(System.currentTimeMillis() + EXPIRATION))
            .signWith(SignatureAlgorithm.HS512, SECRET)
            .compact()
        res.addHeader(HEADER_STRING, "$TOKEN_PREFIX $jwt")
    }

    fun getAuthentication(request: HttpServletRequest): Authentication? {
        val token = request.getHeader(HEADER_STRING)
        if (token != null && token.isNotBlank()) {
            return getAuthentication(token)
        } else {
            throw AuthenticationCredentialsNotFoundException("token is null or empty")
        }
    }

    private fun getAuthentication(token: String): Authentication? {
        val profile = Gson().fromJson(
            Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token.replace(TOKEN_PREFIX, "")).body.subject,
            UserDto::class.java
        )
        return if (profile != null) {
            val newProfile = loadUserById(profile.id)
            return if (newProfile != null) {
                val userRoles = newProfile.roles.map { SimpleGrantedAuthority("ROLE_$it") }.toMutableList()
                UsernamePasswordAuthenticationToken(
                    CustomUser(
                        newProfile,
                        newProfile.id,
                        newProfile.pass,
                        userRoles
                    ), null, userRoles
                )
            } else {
                UsernamePasswordAuthenticationToken(null, null, null)
            }
        } else {
            null
        }
    }

    private fun loadUserById(id: String): UserDto? {
        val user = userService.getById(id)
        user?.let {
            return user
        }
        return null
    }
}
