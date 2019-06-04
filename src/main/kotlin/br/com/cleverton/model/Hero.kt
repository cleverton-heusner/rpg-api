package br.com.cleverton.model

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
data class Hero(@Id
                @GeneratedValue
                val id: Long = 0L,
                @Column(unique = true)
                val name: String = "",
                val clazz: String = "")