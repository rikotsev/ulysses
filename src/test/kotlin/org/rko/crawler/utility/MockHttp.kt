package org.rko.crawler.utility

import java.nio.charset.StandardCharsets

class MockHttp(private val requests: List<MockRequest>) : Http {
    override fun get(url: String, headers: Map<String, String>, params: Map<String, String>): HttpResp {
        val optResponse = requests.stream().filter {
                    it.url() == url &&
                    it.headers() == headers &&
                    it.params() == params
        }.findAny()

        if(optResponse.isEmpty) {
            return HttpResp(ByteArray(0), "", 200)
        }

        val resp = optResponse.get()
        return HttpResp(resp.content().toByteArray(StandardCharsets.UTF_8), StandardCharsets.UTF_8.name(), 200)
    }

    override fun post(url: String, headers: Map<String, String>, params: Map<String, String>): HttpResp {
        TODO("Not yet implemented")
    }

    companion object {
        fun successfulRequest(url: String,
                              headers: Map<String, String> = emptyMap(),
                              params: Map<String, String> = emptyMap(),
                              content: String = ""): MockRequest = MockRequestWithResponse(url, headers, params, content)

        fun errorRequest(url: String,
                         headers: Map<String, String> = emptyMap(),
                         params: Map<String, String> = emptyMap(),
                         exceptionMessage: String = "Error!"): MockRequest = MockRequestWithError(url, headers, params, exceptionMessage)
    }

    interface MockRequest {

        fun url(): String
        fun headers(): Map<String, String>
        fun params(): Map<String, String>
        fun content(): String
    }

    class MockRequestWithResponse(private val url: String,
                                  private val headers: Map<String, String>,
                                  private val params: Map<String, String>,
                                  private val content: String) : MockRequest {
        override fun url(): String = url
        override fun headers(): Map<String, String> = headers
        override fun params(): Map<String, String> = params
        override fun content(): String = content
    }

    class MockRequestWithError(
        private val url: String,
        private val headers: Map<String, String>,
        private val params: Map<String, String>,
        private val exceptionMessage: String
    ) : MockRequest {
        override fun url(): String = url

        override fun headers(): Map<String, String> = headers

        override fun params(): Map<String, String> = params

        override fun content(): String {
            throw Exception(exceptionMessage)
        }

    }
}