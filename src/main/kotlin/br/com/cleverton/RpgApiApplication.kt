package br.com.cleverton

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class RpgApiApplication

fun main(args: Array<String>) {
    runApplication<RpgApiApplication>(*args)
}