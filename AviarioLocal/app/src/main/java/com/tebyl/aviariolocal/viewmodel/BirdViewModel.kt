package com.tebyl.aviariolocal.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.tebyl.aviariolocal.data.Bird
import com.tebyl.aviariolocal.data.BirdRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class BirdViewModel(private val repo: BirdRepository) : ViewModel() {

    val allBirds: StateFlow<List<Bird>> = repo.observeAll()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val searchQuery = MutableStateFlow("")
    val quickFilter = MutableStateFlow("all")
    val activeTags = MutableStateFlow<Set<String>>(emptySet())

    val filteredBirds: StateFlow<List<Bird>> = combine(
        allBirds, searchQuery, quickFilter, activeTags
    ) { birds, query, filter, tags ->
        val recentIds = if (filter == "recent") birds.take(5).mapTo(HashSet()) { it.id } else emptySet()
        birds.filter { bird ->
            val passFilter = when (filter) {
                "fav"    -> bird.isFavorite
                "unid"   -> bird.species == null
                "recent" -> bird.id in recentIds
                else     -> true
            }
            val passTag = tags.isEmpty() || tags.any { it in bird.tags }
            val passSearch = query.isEmpty() ||
                bird.species?.contains(query, ignoreCase = true) == true ||
                bird.location.contains(query, ignoreCase = true) ||
                bird.tags.any { it.contains(query, ignoreCase = true) }
            passFilter && passTag && passSearch
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val allTags: StateFlow<List<String>> = allBirds
        .map { birds ->
            birds.flatMap { it.tags }
                .groupingBy { it }
                .eachCount()
                .entries
                .sortedByDescending { it.value }
                .map { it.key }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun getBirdById(id: Long): StateFlow<Bird?> =
        allBirds
            .map { list -> list.find { it.id == id } }
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    fun toggleFavorite(bird: Bird) {
        viewModelScope.launch { repo.setFavorite(bird.id, !bird.isFavorite) }
    }

    fun upsertBird(bird: Bird) {
        viewModelScope.launch { repo.upsert(bird) }
    }

    fun deleteBird(bird: Bird) {
        viewModelScope.launch { repo.delete(bird) }
    }

    companion object {
        fun factory(repo: BirdRepository) = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T =
                BirdViewModel(repo) as T
        }
    }
}
