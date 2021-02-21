package org.marzieh

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.builder.SpringApplicationBuilder

@SpringBootApplication
class WebApplication

fun main() {

    SpringApplicationBuilder(WebApplication::class.java).run()

}