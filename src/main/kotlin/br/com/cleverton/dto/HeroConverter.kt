package br.com.cleverton.dto

import br.com.cleverton.model.Hero
import org.springframework.stereotype.Component

@Component
class HeroConverter {

    fun toDTO(hero: Hero): HeroDTO {
        return HeroDTO(hero.id, hero.name, hero.clazz)
    }

    fun toModelToAdd(heroDTO: HeroDTO): Hero {
        return Hero(name = heroDTO.name, clazz = heroDTO.clazz)
    }

    fun toExistingModel(id: Long, heroDTO: HeroDTO): Hero {
        return Hero(id, heroDTO.name, heroDTO.clazz)
    }
}