package com.ianarbuckle.rickmortycompose.ui.characters.repository

import com.ianarbuckle.rickmortycompose.api.RickMortyService
import javax.inject.Inject

class CharactersRepository @Inject constructor(private val service: RickMortyService) {

    suspend fun characters(page: Int) = service.retrieveCharacters(page)
}