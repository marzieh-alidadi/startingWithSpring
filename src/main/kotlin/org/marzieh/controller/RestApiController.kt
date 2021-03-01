package org.marzieh.controller

import org.marzieh.annotation.ApiMapping
import org.marzieh.dto.UserCreateDto
import org.marzieh.dto.UserDto
import org.marzieh.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

/*
var linkOfFilm="";

@ApiMapping
class RestApiController {
    @PostMapping("rest/{name}")
    fun post(
        @PathVariable name: String
    ): ResponseEntity<String> {
        when (name) {
            "film1" -> linkOfFilm="link: https://linkOfFilm1"
            "film2" -> linkOfFilm="link: https://linkOfFilm2"
            "film3" -> linkOfFilm="link: https://linkOfFilm3"
            "film4" -> linkOfFilm="link: https://linkOfFilm4"
            "film5" -> linkOfFilm="link: https://linkOfFilm5"
            else -> linkOfFilm="Sorry!!! The requested film is not available:("
        }
        return ResponseEntity(linkOfFilm, HttpStatus.OK)
    }
}
 */


@ApiMapping
class RestApiController @Autowired constructor(
    private val userService: UserService
) {
    @PostMapping("user")
    fun save(@RequestBody create: UserCreateDto): ResponseEntity<UserDto> {
        return ResponseEntity(userService.save(create), HttpStatus.CREATED)
    }

    @GetMapping("user/{id}")
    fun save(@PathVariable id: String): ResponseEntity<UserDto> {
        val user = userService.get(id)
        return if (user == null) {
            ResponseEntity(HttpStatus.NOT_FOUND)
        } else {
            ResponseEntity(user, HttpStatus.OK)
        }
    }
}