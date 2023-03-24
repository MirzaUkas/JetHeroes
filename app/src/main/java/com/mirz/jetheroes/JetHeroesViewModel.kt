package com.mirz.jetheroes

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mirz.jetheroes.data.HeroRepository
import com.mirz.jetheroes.model.Hero
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class JetHeroesViewModel(private val heroRepository: HeroRepository) : ViewModel() {
    private val _groupedHeroes = MutableStateFlow(heroRepository.getHeroes().sortedBy { it.name }
        .groupBy { it.name.first() })

    val groupedHeroes: StateFlow<Map<Char, List<Hero>>> get() = _groupedHeroes

    private val _query = mutableStateOf("")
    val query: String get() = _query.value
    fun searchHeroes(query: String) {
        _query.value = query
        _groupedHeroes.value = heroRepository.searchHeroes(query).sortedBy { it.name }
            .groupBy { it.name.first() }
    }
}

class ViewModelFactory(private val repository: HeroRepository) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(JetHeroesViewModel::class.java)) {
            return JetHeroesViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}