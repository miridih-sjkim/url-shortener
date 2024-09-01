package com.miridih.shortenurl.shorten_url.ui

import com.miridih.shortenurl.shorten_url.app.ShortenUrlService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class ShortenUrlApi(
    private val shortenUrlService: ShortenUrlService
) {
    @GetMapping("/{shortenUrl}")
    fun shortenUrl(@PathVariable shortenUrl: String): ResponseEntity<Void>? {
        this.shortenUrlService.getOriginalUrl(shortenUrl)?.let {
            return ResponseEntity.status(301).header("Location", it).build()
//            return ResponseEntity.status(302).header("Location", it).build()
        }

        return ResponseEntity.notFound().build()
    }

    @PostMapping("/shorten-url")
    fun createShortenUrl(originalUrl: String): String {
        return shortenUrlService.create(originalUrl)
    }
}
