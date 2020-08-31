package com.ianarbuckle.rickmortycompose.ui.characters

import com.ianarbuckle.rickmortycompose.api.Character
import com.ianarbuckle.rickmortycompose.utils.Result
import javax.inject.Inject

class CharactersUseCase @Inject constructor(private val repository: CharactersRepository) {

    suspend fun fetchConferences(): Result<List<Character>> = repository.fetchCharacters()
}