package com.miridih.shortenurl.shorten_url.app

import com.miridih.shortenurl.shorten_url.domain.ShortenUrl
import mu.KotlinLogging
import org.springframework.stereotype.Service
import kotlin.math.pow

private val log = KotlinLogging.logger {}

/**
 * Base62 기반 단축 URL 서비스
 */
//@Service
class Base62BasedShortenUrlService(
    private val shortenUrlRepository: ShortenUrlRepository,
) : ShortenUrlService {

    override fun create(originalUrl: String): String {
        // 이미 단축 URL이 생성되어 있는 경우, 그 값을 반환
        this.shortenUrlRepository.findByOriginalUrl(originalUrl)?.let {
            return it.shortenUrl
        }

        // 다음 식별자 값 조회
        val nextVal = this.shortenUrlRepository.getNextVal()
        // 단축 URL 생성
        return this.shortenUrlRepository.save(
            ShortenUrl(
                id = nextVal,
                originalUrl = originalUrl,
                shortenUrl = base62Encode(nextVal)
            )
        ).let {
            log.info { "Shorten URL created: ${it.shortenUrl}" }
            it.shortenUrl
        }
    }

    override fun getOriginalUrl(shortenUrl: String): String? {
        return this.shortenUrlRepository.findById(base62Decode(shortenUrl)).orElse(null)?.let {
            log.info { "Original URL found: ${it.originalUrl}" }
            return it.originalUrl
        }
    }
}

private fun base62Encode(param: Long): String {
    var num = param
    val sb = StringBuilder()
    while (num > 0) {
        sb.append(CODEC[(num % RADIX).toInt()])
        num /= RADIX
    }
    return sb.reverse().toString()
}

private fun base62Decode(str: String): Long {
    var num: Long = 0
    for (i in str.indices) {
        num += CODEC.indexOf(str[i]).toLong() * RADIX.toDouble().pow((str.length - i - 1).toDouble()).toLong()
    }
    return num
}

private const val RADIX = 62
private const val CODEC = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"
