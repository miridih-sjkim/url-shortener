package com.miridih.shortenurl.shorten_url.app

import mu.KotlinLogging
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

private val log = KotlinLogging.logger {}

/**
 * 단축 URL 서비스
 */
interface ShortenUrlService {
    /**
     * 단축 URL 생성
     */
    fun create(originalUrl: String): String

    /**
     * 단축 URL 을 통해 원본 URL 조회
     */
    fun getOriginalUrl(shortenUrl: String): String?
}


