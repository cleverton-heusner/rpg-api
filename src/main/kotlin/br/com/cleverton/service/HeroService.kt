package br.com.cleverton.service

import br.com.cleverton.config.HeroValidationMessages
import br.com.cleverton.dto.HeroConverter
import br.com.cleverton.dto.HeroDTO
import br.com.cleverton.exception.ConflictException
import br.com.cleverton.exception.NotFoundException
import br.com.cleverton.repository.HeroRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class HeroService {

    @Autowired
    lateinit var repository: HeroRepository

    @Autowired
    lateinit var heroConverter: HeroConverter

    @Autowired
    lateinit var heroValidationMessages: HeroValidationMessages

    fun list(): List<HeroDTO> {
        val heroes = repository.findAll().toList()
        return heroes.map { hero -> heroConverter.toDTO(hero) }
    }

    fun findById(id: Long): HeroDTO {
        val optionalHero = repository.findById(id)
        if (!optionalHero.isPresent) {
            throw NotFoundException(heroValidationMessages.notFound)
        }

        return heroConverter.toDTO(optionalHero.get())
    }

    @Throws(ConflictException::class)
    fun add(heroDTO: HeroDTO): HeroDTO {
        if (repository.existsByName(heroDTO.name)) {
            throw ConflictException(heroValidationMessages.nameInUse)
        }

        val heroToAdd = heroConverter.toModelToAdd(heroDTO)
        val newHero = repository.save(heroToAdd)
        return heroConverter.toDTO(newHero)
    }

    @Throws(NotFoundException::class, ConflictException::class)
    fun update(id: Long, heroDTO: HeroDTO): HeroDTO {
        val optionalHero = repository.findByName(heroDTO.name)
        if (optionalHero.isPresent && optionalHero.get().id != id) {
            throw ConflictException(heroValidationMessages.nameInUse)
        }

        if (repository.existsById(id)) {
            val hero = heroConverter.toExistingModel(id, heroDTO)
            val updatedHero = repository.save(hero)
            return heroConverter.toDTO(updatedHero)
        } else {
            throw NotFoundException(heroValidationMessages.notFound)
        }

        return HeroDTO()
    }

    @Throws(NotFoundException::class)
    fun deleteById(id: Long): Unit {
        if (repository.existsById(id)) {
            repository.deleteById(id)
        } else {
            throw NotFoundException(heroValidationMessages.notFound)
        }
    }
}