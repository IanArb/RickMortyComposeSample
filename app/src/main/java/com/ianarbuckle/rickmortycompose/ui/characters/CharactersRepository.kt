package com.ianarbuckle.rickmortycompose.ui.characters

import com.ianarbuckle.rickmortycompose.api.Character
import com.ianarbuckle.rickmortycompose.api.RickMortyService
import com.ianarbuckle.rickmortycompose.utils.Result
import com.ianarbuckle.rickmortycompose.utils.safeApiCall
import java.io.IOException
import javax.inject.Inject

class CharactersRepository @Inject constructor(private val service: RickMortyService) {

    private suspend fun retrieveCharacters(): Result<List<Character>> {
        val result = service.retrieveCharacters().characters

        return when {
            !result.isNullOrEmpty() -> Result.Success(result)
            else -> Result.Error(IOException("Error retrieving characters"))
        }
    }

    suspend fun fetchCharacters() = safeApiCall(
        call = {
            retrieveCharacters()
        },
        errorMessage = "Failed to retrieve characters"
    )
}