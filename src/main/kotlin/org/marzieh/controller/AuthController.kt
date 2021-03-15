package org.marzieh.controller

import io.swagger.v3.oas.annotations.Parameter
import org.marzieh.annotation.ApiMapping
import org.marzieh.config.mongoDB.TenantContext
import org.marzieh.dto.*
import org.marzieh.service.TokenAuthenticationService
import org.marzieh.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletResponse

@ApiMapping
class AuthController @Autowired constructor(
    private val authenticationManager: AuthenticationManager,
    private val passwordEncoder: PasswordEncoder,
    private val userService: UserService
) {
    @GetMapping("auth/profile")
    fun profile(@Parameter(hidden = true) @AuthenticationPrincipal user: CustomUser): ResponseEntity<ProfileResponse?> {
        val currentUser = userService.getById(user.profile.id)
        return if (currentUser != null) {
            ResponseEntity(
                ProfileResponse(
                    user.profile.id,
                    currentUser.phone,
                    currentUser.name,
                    currentUser.family,
                    currentUser.roles
                ), HttpStatus.OK
            )
        } else {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

    @PostMapping("auth/register")
    fun register(@RequestBody userRequest: UserCreateDto): ResponseEntity<UserDto?> {
        userRequest.pass = passwordEncoder.encode(userRequest.pass)
        return ResponseEntity(userService.save(userRequest), HttpStatus.OK)
    }

    @PostMapping("auth/login")
    fun login(
        @RequestBody login: LoginDto,
        @Autowired response: HttpServletResponse
    ): ResponseEntity<ProfileResponse?>? {
        val principal = authenticationManager.authenticate(UsernamePasswordAuthenticationToken(login.phone, login.pass))
        if (principal?.isAuthenticated == true) {
            SecurityContextHolder.getContext().authentication = principal
            TokenAuthenticationService.addAuthentication(response, principal)
            return profile(principal.principal as CustomUser)
        }
        response.status = HttpStatus.BAD_REQUEST.value()
        return null
    }


}

