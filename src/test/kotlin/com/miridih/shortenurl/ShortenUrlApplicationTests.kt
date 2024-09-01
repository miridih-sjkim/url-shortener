package com.miridih.shortenurl

import mu.KotlinLogging
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

private val log = KotlinLogging.logger {}

//@SpringBootTest
class ShortenUrlApplicationTests {
	@Test
	fun test() {
		log.info("Test 1")
		Assertions.assertThat(true).isTrue()
	}
}
