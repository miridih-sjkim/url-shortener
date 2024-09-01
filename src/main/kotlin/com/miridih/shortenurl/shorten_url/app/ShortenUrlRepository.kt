package com.miridih.shortenurl.shorten_url.app

import com.miridih.shortenurl.shorten_url.domain.ShortenUrl
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface ShortenUrlRepository: JpaRepository<ShortenUrl, Long> {
    /**
     * 단축 url sequence 조회
     */
    @Query(value = "SELECT NEXTVAL('shorten_url_id_seq')", nativeQuery = true)
    fun getNextVal(): Long

    fun findByOriginalUrl(originalUrl: String): ShortenUrl?

    fun findByShortenUrl(shortenUrl: String): ShortenUrl?
}
