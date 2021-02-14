package com.ianarbuckle.rickmortycompose.ui.characters.datasource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.ianarbuckle.rickmortycompose.api.Character
import com.ianarbuckle.rickmortycompose.ui.characters.repository.CharactersRepository
import com.ianarbuckle.rickmortycompose.utils.safeApiCall
import java.io.IOException
import javax.inject.Inject

class CharactersPagingSource @Inject constructor(private val repository: CharactersRepository): PagingSource<Int, Character>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Character> {
        return try {
            val page = params.key ?: 1

            val response = repository.characters(page)
            val characters = response.characters

            val prevKey = if (page > 0) page - 1 else null
            val nextKey = if (response.info.next != null) page + 1 else null

            LoadResult.Page(data = characters, prevKey = prevKey, nextKey = nextKey)
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Character>): Int? {
        return state.anchorPosition
    }
}