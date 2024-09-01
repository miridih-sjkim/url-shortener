package com.miridih.shortenurl.shorten_url.domain

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.SequenceGenerator

/**
 * 단축 URL
 */
@Entity
class ShortenUrl(
    /**
     * 원본 URL
     */
    val originalUrl: String,

    /**
     * 단축 URL
     */
    val shortenUrl: String,

    /**
     * 식별자
     */
    @Id
    val id: Long? = null,
) {
}
