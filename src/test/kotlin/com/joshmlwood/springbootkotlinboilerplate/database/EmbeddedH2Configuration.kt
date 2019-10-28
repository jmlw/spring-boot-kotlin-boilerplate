package com.joshmlwood.springbootkotlinboilerplate.database

import org.h2.tools.Server
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Import
import org.springframework.core.annotation.AliasFor
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.transaction.annotation.EnableTransactionManagement
import java.io.IOException
import javax.sql.DataSource
import kotlin.reflect.KClass


@DataJpaTest
@ExtendWith(SpringExtension::class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(EmbeddedH2Configuration::class)
annotation class H2DatabaseTest(
    @get:AliasFor(annotation = ContextConfiguration::class, attribute = "classes")
    val classes: Array<KClass<*>> = [],
    @get:AliasFor(annotation = EntityScan::class, attribute = "basePackages")
    val entityScan: Array<String> = [],
    @get:AliasFor(annotation = EnableJpaRepositories::class, attribute = "basePackages")
    val jpaRepositories: Array<String> = [],
    @get:AliasFor(annotation = EnableJpaRepositories::class, attribute = "includeFilters")
    val jpaIncludeFilters: Array<ComponentScan.Filter> = [],
    @get:AliasFor(annotation = EnableJpaRepositories::class, attribute = "excludeFilters")
    val jpaExcludeFilters: Array<ComponentScan.Filter> = []
)

@EnableTransactionManagement
class EmbeddedH2Configuration {
    init {
        try {
            database = Server.createTcpServer("-tcpAllowOthers", "-ifNotExists", "-tcpPassword", pass).start()
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }

    @Bean
    fun testDataSource(): DataSource {
        return DataSourceBuilder.create()
            .username(user)
            .password(pass)
            .url(url)
            .build()
    }

    companion object {
        private var database: Server? = null
        private val url = "jdbc:h2:tcp://localhost/~/h2"
        private val user = "sa"
        private val pass = ""
    }
}
