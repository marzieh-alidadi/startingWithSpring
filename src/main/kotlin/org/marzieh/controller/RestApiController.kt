package org.marzieh.controller

import org.marzieh.annotation.ApiMapping
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

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