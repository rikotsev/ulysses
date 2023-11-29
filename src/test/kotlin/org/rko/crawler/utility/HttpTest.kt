package org.rko.crawler.utility

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class HttpTest {

    @Test
    fun testBuildQueryStringParametersNominal() {
        val actual =
            HttpImpl().buildQueryStringParameters(mapOf("param1" to "value1", "param2" to "value2"))

        assertEquals("?param1=value1&param2=value2", actual)
    }

    @Test
    fun testBuildQueryStringParametersEmpty() {
        val actual = HttpImpl().buildQueryStringParameters(emptyMap())

        assertEquals("", actual)
    }

}