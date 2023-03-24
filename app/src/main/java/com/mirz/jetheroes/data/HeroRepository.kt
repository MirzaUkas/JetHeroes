package com.mirz.jetheroes.data

import com.mirz.jetheroes.model.Hero
import com.mirz.jetheroes.model.HeroesData

class HeroRepository {
    fun getHeroes(): List<Hero> {
        return HeroesData.heroes
    }
    fun searchHeroes(query: String): List<Hero> {
        return HeroesData.heroes.filter { it.name.contains(query, true) }
    }
}