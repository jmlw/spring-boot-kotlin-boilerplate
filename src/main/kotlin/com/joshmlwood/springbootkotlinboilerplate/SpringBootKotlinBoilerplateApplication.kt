package com.joshmlwood.springbootkotlinboilerplate

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SpringBootKotlinBoilerplateApplication

fun main(args: Array<String>) {
    runApplication<SpringBootKotlinBoilerplateApplication>(*args)
}
