package br.com.cleverton.dto

import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.hateoas.ResourceSupport
import javax.validation.constraints.NotBlank

data class HeroDTO(@JsonProperty(value = "id", access = JsonProperty.Access.READ_ONLY)
                   val id: Long = 0L,

                   @get:NotBlank(message = "{hero.name.not_blank}")
                   val name: String = "",

                   @get:NotBlank(message = "{hero.clazz.not_blank}")
                   val clazz: String = "") : ResourceSupport()