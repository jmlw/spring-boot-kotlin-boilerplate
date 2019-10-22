package com.joshmlwood.springbootkotlinboilerplate.configuration

import com.joshmlwood.springbootkotlinboilerplate.SpringBootKotlinBoilerplateApplication
import io.github.swagger2markup.Swagger2MarkupConverter
import io.github.swagger2markup.builder.Swagger2MarkupConfigBuilder
import io.github.swagger2markup.markup.builder.MarkupLanguage
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import java.nio.file.Paths

@SpringBootTest(classes = [SpringBootKotlinBoilerplateApplication::class])
class SwaggerDocGenerationTest {

    @Autowired
    private val context: WebApplicationContext? = null

    private var mockMvc: MockMvc? = null

    @BeforeEach
    fun setUp() {
        this.mockMvc = this.context?.let { MockMvcBuilders.webAppContextSetup(it).build() }
    }

    @Test
    fun convertSwaggerToAsciiDoc() {
        val config = Swagger2MarkupConfigBuilder()
            .withMarkupLanguage(MarkupLanguage.ASCIIDOC)
            .build()
        this.mockMvc?.perform(
            get("/v2/api-docs")
                .accept(MediaType.APPLICATION_JSON)
        )?.andDo {
            Swagger2MarkupConverter.from(it.response.contentAsString)
                .withConfig(config)
                .build()
                .toFolder(Paths.get("build/docs/asciidoc/generated"))
        }?.andExpect(status().isOk)
    }

    @Test
    fun convertSwaggerToMarkdown() {
        val config = Swagger2MarkupConfigBuilder()
            .withMarkupLanguage(MarkupLanguage.MARKDOWN)
            .build()
        this.mockMvc?.perform(
            get("/v2/api-docs")
                .accept(MediaType.APPLICATION_JSON)
        )?.andDo {
            Swagger2MarkupConverter.from(it.response.contentAsString)
                .withConfig(config)
                .build()
                .toFolder(Paths.get("build/docs/asciidoc/generated"))
        }?.andExpect(status().isOk)
    }
}
