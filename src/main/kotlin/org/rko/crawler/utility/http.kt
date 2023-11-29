package org.rko.crawler.utility

import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.nio.charset.StandardCharsets

private const val CONTENT_TYPE_HEADER = "Content-Type"

interface Http {
    fun get(url: String, headers: Map<String, String> = emptyMap(), params: Map<String, String> = emptyMap()): HttpResp
    fun post(url: String, headers: Map<String, String> = emptyMap(), params: Map<String, String> = emptyMap()): HttpResp
}

fun http() : Http = HttpImpl()

private fun main() {
    val http = http()
    val response = http.get("https://finnhub.io/api/v1/news",
                            mapOf("X-Finnhub-Token" to "clcd511r01qk5dvqdcngclcd511r01qk5dvqdco0"),
                            mapOf("category" to "news"))

    println("response = ${String(response.content, StandardCharsets.UTF_8)}")
}


data class HttpResp(val content: ByteArray, val encoding: String, val statusCode: Int) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as HttpResp

        if (!content.contentEquals(other.content)) return false
        if (encoding != other.encoding) return false
        if (statusCode != other.statusCode) return false

        return true
    }

    override fun hashCode(): Int {
        var result = content.contentHashCode()
        result = 31 * result + encoding.hashCode()
        result = 31 * result + statusCode
        return result
    }


}

class HttpImpl : Http {

    private val httpClient: HttpClient = HttpClient.newHttpClient()

    override fun get(url: String, headers: Map<String, String>, params: Map<String, String>): HttpResp {
        val requestBuilder = HttpRequest.newBuilder().GET()

        requestBuilder.uri(URI.create(url))
        headers.forEach(requestBuilder::setHeader)

        val response = httpClient.send(requestBuilder.build(), HttpResponse.BodyHandlers.ofByteArray())

        return HttpResp(response.body(),
            response.headers().firstValue(CONTENT_TYPE_HEADER).orElse(StandardCharsets.UTF_8.displayName()),
            response.statusCode())
    }

    fun buildQueryStringParameters(params: Map<String, String>): String {
        if(params.isEmpty()) {
            return ""
        }

        return "?" + params.entries.joinToString(separator = "&", transform = { it.key + "=" + it.value })
    }

    override fun post(url: String, headers: Map<String, String>, params: Map<String, String>): HttpResp {
        TODO("Not yet implemented")
    }

}