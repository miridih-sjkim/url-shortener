package com.miridih.shortenurl.shorten_url.app

import com.miridih.shortenurl.shorten_url.domain.ShortenUrl
import org.springframework.stereotype.Service
import java.security.MessageDigest

/**
 * 해시 기반 단축 URL 서비스
 */
 @Service
class HashBasedShortenUrlService(
    private val shortenUrlRepository: ShortenUrlRepository
): ShortenUrlService {
    override fun create(originalUrl: String): String {
        // 이미 단축 URL이 생성되어 있는 경우, 그 값을 반환
        this.shortenUrlRepository.findByOriginalUrl(originalUrl)?.let { return it.shortenUrl }

        // 이미 생성된 단축 URL 이 없으면 새로 생성
        return this.createUniqueShortenUrl(originalUrl)
    }

    override fun getOriginalUrl(shortenUrl: String): String? {
        return shortenUrlRepository.findByShortenUrl(shortenUrl)?.originalUrl
    }

    private fun createUniqueShortenUrl(originalUrl: String): String {
        val hashKey = sha256Encode(originalUrl)
        // hash 충돌이 없으면 생성
        if (this.shortenUrlRepository.findByShortenUrl(hashKey) == null) {
            return this.shortenUrlRepository.save(
                ShortenUrl(
                    originalUrl = originalUrl,
                    shortenUrl = hashKey,
                    id = this.shortenUrlRepository.getNextVal(),
                )
            ).shortenUrl
        }

        // hash 충돌이 있으면 다시 생성
        return createUniqueShortenUrl(appendString(originalUrl))
    }

    private fun appendString(originalUrl: String): String {
        if (originalUrl.contains("?")) {
            return "$originalUrl&$appendString"
        }

        return "$originalUrl?$appendString"
    }
}

private fun sha256Encode(param: String): String {
    val digest: MessageDigest = MessageDigest.getInstance("SHA-256")
    val hash = digest.digest(param.toByteArray())

    val hexString = StringBuffer()
    for (b in hash) {
        hexString.append(String.format("%02x", b))
    }

    return hexString.substring(0, 7)
}

private const val appendString = "_a=a"
