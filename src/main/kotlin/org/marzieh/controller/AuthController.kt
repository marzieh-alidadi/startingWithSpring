package org.marzieh.controller

import com.google.gson.Gson
import io.jsonwebtoken.Jwt
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.swagger.v3.oas.annotations.Parameter
import org.marzieh.annotation.ApiMapping
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
import java.util.*
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletResponse

@ApiMapping
@RequestMapping("auth")
class AuthController @Autowired constructor(
    private val authenticationManager: AuthenticationManager,
    private val passwordEncoder: PasswordEncoder,
    private val userService: UserService
) {
    /*
    @GetMapping("profile")
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
         */

    @PostMapping("register")
    fun register(@RequestBody userRequest: UserCreateDto): ResponseEntity<UserDto?> {
        userRequest.pass = passwordEncoder.encode(userRequest.pass)
        return ResponseEntity(userService.save(userRequest), HttpStatus.OK)
    }


/*
    @PostMapping("login")
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

   */

    @PostMapping("login")
    fun login(@RequestBody login: LoginDto, response: HttpServletResponse): ResponseEntity<Any>{
        val user = userService.getByPhone(login.phone)
            ?: return ResponseEntity.badRequest().body("User not found!")

        val y : Boolean = passwordEncoder.matches(login.pass, user.pass)
        if(!y){
            return ResponseEntity.badRequest().body("Invalid password")
        }

        val issuer = user.id.toString()

        val jwt = Jwts.builder()
            .setIssuer(issuer)
            .setExpiration(Date(System.currentTimeMillis() + 60 * 24 * 1000))
            .signWith(SignatureAlgorithm.HS512, "SECRET")
            .compact()

        val cookie = Cookie("jwt", jwt)
        cookie.isHttpOnly = true

        response.addCookie(cookie)

        return ResponseEntity("Successful!", HttpStatus.OK)
    }

    @GetMapping("profile")
    fun profile(@CookieValue("jwt") jwt: String?): ResponseEntity<Any>{
        try {
            if(jwt == null){
                return ResponseEntity("Unauthenticated!", HttpStatus.UNAUTHORIZED)
            }
            val body = Jwts.parser().setSigningKey("SECRET").parseClaimsJws(jwt).body
            return ResponseEntity(userService.getById(body.issuer)?.toPR(), HttpStatus.OK)
        }catch (e: Exception){
            return ResponseEntity("Unauthenticated!", HttpStatus.UNAUTHORIZED)
        }
    }

    @GetMapping("logout")
    fun logout(response: HttpServletResponse): ResponseEntity<Any>{

        val cookie = Cookie("jwt", "")
        cookie.maxAge = 0

        response.addCookie(cookie)

        return ResponseEntity("Successful!", HttpStatus.OK)
    }


}

