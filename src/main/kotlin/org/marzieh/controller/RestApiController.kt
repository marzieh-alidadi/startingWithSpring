package org.marzieh.controller

import org.marzieh.annotation.ApiMapping
import org.marzieh.config.mongoDB.TenantContext
import org.marzieh.dto.UserCreateDto
import org.marzieh.dto.UserDto
import org.marzieh.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@ApiMapping
class RestApiController @Autowired constructor(
    private val userService: UserService
) {
    @PostMapping("item/addItem")
    fun save(@RequestParam tenant: String, @RequestBody create: UserCreateDto): ResponseEntity<UserDto> {
        TenantContext.setCurrentTenant(tenant)
        return ResponseEntity(userService.save(create), HttpStatus.CREATED)
    }

    @GetMapping("item/getByTenant/{tenant}")
    fun get(@RequestParam tenant: String): ResponseEntity<List<UserDto>> {
        TenantContext.setCurrentTenant(tenant)
        return ResponseEntity(userService.getAll(), HttpStatus.OK)
    }

    @GetMapping("item/getByID/{id}")
    fun getById(@RequestParam tenant: String,  @PathVariable id: String): ResponseEntity<UserDto> {
        TenantContext.setCurrentTenant(tenant)
        val user = userService.get(id)
        return if (user == null) {
            ResponseEntity(HttpStatus.NOT_FOUND)
        } else {
            ResponseEntity(user, HttpStatus.OK)
        }
    }


    @GetMapping("item/deleteByID/{id}")
    fun deleteByID(@RequestParam tenant: String, @PathVariable id: String): ResponseEntity<String> {
        TenantContext.setCurrentTenant(tenant)
        val user = userService.get(id)
        return if (user == null) {
            ResponseEntity("Not found!!",HttpStatus.NOT_FOUND)
        } else {
            userService.delete(id)
            ResponseEntity("Successfully deleted!", HttpStatus.OK)
        }
    }

}


/*

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

    /*
    @PostMapping("user")
    fun save(@RequestBody create: UserCreateDto): ResponseEntity<UserDto> {
        return ResponseEntity(userService.save(create), HttpStatus.CREATED)
    }
     */

    /*
    @GetMapping("user/{id}")
    fun get(@PathVariable id: String): ResponseEntity<UserDto> {
        val user = userService.get(id)
        return if (user == null) {
            ResponseEntity(HttpStatus.NOT_FOUND)
        } else {
            ResponseEntity(user, HttpStatus.OK)
        }
    }
    */

    @PostMapping("user")
    fun save(@RequestParam tenant: String, @RequestBody create: UserCreateDto): ResponseEntity<UserDto> {
        TenantContext.setCurrentTenant(tenant)
        return ResponseEntity(userService.save(create), HttpStatus.CREATED)
    }

    @GetMapping("user")
    fun get(): ResponseEntity<List<UserDto>> {
        return ResponseEntity(userService.getAll(), HttpStatus.OK)
    }

    @GetMapping("user/getById/{id}")
    fun getById(@PathVariable id: String): ResponseEntity<UserDto> {
        val user = userService.get(id)
        return if (user == null) {
            ResponseEntity(HttpStatus.NOT_FOUND)
        } else {
            ResponseEntity(user, HttpStatus.OK)
        }
    }

}

 */