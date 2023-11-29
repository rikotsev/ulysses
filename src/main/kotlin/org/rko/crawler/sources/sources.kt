package org.rko.crawler.sources

interface NewsSource {
    fun drain(): List<NewsArticle>
}

data class NewsArticle(val url: String, val summary: String)

sealed interface ApiKey {
    fun get(): String

    class ApiKeyFromEnvVar(private val variableName: String) : ApiKey {
        override fun get(): String = System.getenv(variableName)
    }

    class ApiKeyFromProperty(private val propertyName: String) : ApiKey {
        override fun get(): String = System.getProperty(propertyName)
    }

    class ApiKeyHardcoded(private val apiKey: String) : ApiKey {
        override fun get(): String = apiKey
    }
}