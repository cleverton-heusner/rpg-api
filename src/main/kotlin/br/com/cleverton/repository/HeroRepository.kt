package br.com.cleverton.repository

import br.com.cleverton.model.Hero
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface HeroRepository : JpaRepository<Hero, Long> {
    fun findByName(name: String) : Optional<Hero>

    fun existsByName(name: String): Boolean
}