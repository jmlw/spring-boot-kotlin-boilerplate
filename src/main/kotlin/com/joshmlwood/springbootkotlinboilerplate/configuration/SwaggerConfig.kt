package com.joshmlwood.springbootkotlinboilerplate.configuration

import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import springfox.documentation.swagger2.annotations.EnableSwagger2

@Configuration
@EnableSwagger2
@Profile("swagger")
class SwaggerConfig
