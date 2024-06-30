package com.example.auth

import io.github.cdimascio.dotenv.Dotenv
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration
import org.springframework.boot.runApplication

@SpringBootApplication(exclude = [SecurityAutoConfiguration::class])
class ExampleAuthApplication

fun main(args: Array<String>) {
	configureEnv()
	runApplication<ExampleAuthApplication>(*args)
}

private fun configureEnv() {
	val dotenv = Dotenv.load()
	dotenv.entries().forEach { entry ->
		System.setProperty(entry.key, entry.value)
	}
}