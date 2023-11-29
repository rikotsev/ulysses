package org.rko.crawler.sources.finnhub

import org.rko.crawler.sources.NewsArticle
import org.rko.crawler.sources.NewsSource
import org.rko.crawler.utility.Http
import org.rko.crawler.utility.HttpImpl

class FinnhubNewsSource(private val http: Http = HttpImpl()) : NewsSource {
    override fun drain(): List<NewsArticle> {
        TODO("Not yet implemented")
    }

}