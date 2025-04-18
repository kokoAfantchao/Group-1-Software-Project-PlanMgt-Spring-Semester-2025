package com.team1.dev

import com.team1.dev.controllers.HelloController;

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest

// Run the test suite using the Gradle wrapper: ./gradlew test
// (Make sure your current directory is in Back-End

@SpringBootTest
class PmApplicationTests {

	@Test
	fun contextLoads() {
	}

	@Test
	fun test_HelloEndpoint() {
		val c = HelloController().sayHello()

		assert (c.message == "Hello world!")
	}
}
