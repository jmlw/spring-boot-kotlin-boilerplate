package com.joshmlwood.springbootkotlinboilerplate.database

import com.joshmlwood.springbootkotlinboilerplate.SpringBootKotlinBoilerplateApplication
import org.h2.jdbc.JdbcSQLSyntaxErrorException
import org.junit.Assert.assertNotNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import javax.sql.DataSource

@H2DatabaseTest(
    classes = [SpringBootKotlinBoilerplateApplication::class]
)
class DataTest {

    @Autowired
    private val dataSource: DataSource? = null

    @Test
    fun `given datasource expect datasource is not null`() {
        assertNotNull(dataSource)
    }

    @Test
    fun `given datasource expect health select succeeds`() {
        dataSource!!.connection!!
            .prepareStatement("SELECT 1")!!
            .execute()
    }

    @Test
    fun `given table does not exist expect select to fail`() {
        assertThrows<JdbcSQLSyntaxErrorException> {
            dataSource!!
                .connection!!
                .prepareStatement("SELECT * FROM DOES_NOT_EXIST")!!
                .execute()
        }

    }
}
