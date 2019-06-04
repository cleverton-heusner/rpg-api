package br.com.cleverton.controller

import br.com.cleverton.dto.HeroDTO
import br.com.cleverton.service.HeroService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.hateoas.Link
import org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("heroes")
class HeroController {

    private val LINK_RELATION_ALL_HEROES = "All Heroes"
    private val LINK_RELATION_ADD_HERO = "Add Hero"

    @Autowired
    lateinit var service: HeroService

    @GetMapping
    fun list(): ResponseEntity<Any> {
        val heroesDTO = service.list()
        heroesDTO.map { heroDTO ->
            heroDTO.add(getHeroDetailsLink(heroDTO.id), getHeroAdditionLink(),
                    getHeroUpdateLink(heroDTO.id), getHeroDeletionLink(heroDTO.id))
        }
        return ResponseEntity.ok(heroesDTO)
    }

    private fun getHeroDetailsLink(id: Long?): Link {
        return linkTo(HeroController::class.java).slash(id)
                .withSelfRel()
                .withType("GET")
    }

    private fun getHeroAdditionLink(): Link {
        return linkTo(HeroController::class.java).withRel(LINK_RELATION_ADD_HERO)
                .withType("POST")
    }

    private fun getHeroUpdateLink(id: Long?): Link {
        return linkTo(HeroController::class.java).slash(id)
                .withSelfRel()
                .withType("UPDATE")
    }

    private fun getHeroDeletionLink(id: Long?): Link {
        return linkTo(HeroController::class.java).slash(id)
                .withSelfRel()
                .withType("DELETE")
    }

    @GetMapping("{id}")
    fun findById(@PathVariable id: Long): ResponseEntity<Any> {
        val heroDTO = service.findById(id)
        heroDTO.add(getHeroesLink())
        return ResponseEntity.ok(heroDTO)
    }

    private fun getHeroesLink(): Link {
        return linkTo(HeroController::class.java).withRel(LINK_RELATION_ALL_HEROES)
                .withType("GET")
    }

    @PostMapping
    fun add(@RequestBody @Valid heroDTO: HeroDTO): ResponseEntity<Any> {
        val newHeroDTO = service.add(heroDTO)
        newHeroDTO.add(getHeroesLink())
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(newHeroDTO)
    }

    @PutMapping("{id}")
    fun update(@PathVariable id: Long, @RequestBody @Valid heroDTO: HeroDTO): ResponseEntity<Any> {
        val updatedHeroDTO = service.update(id, heroDTO)
        updatedHeroDTO.add(getHeroesLink())
        return ResponseEntity.ok(updatedHeroDTO)
    }

    @DeleteMapping("{id}")
    fun delete(@PathVariable id: Long): ResponseEntity<Any> {
        service.deleteById(id)
        return ResponseEntity.noContent()
                .build()
    }
}