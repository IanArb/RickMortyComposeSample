package com.ianarbuckle.rickmortycompose.ui.characters.viewmodel

import androidx.lifecycle.ViewModel
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.ianarbuckle.rickmortycompose.api.Character
import com.ianarbuckle.rickmortycompose.ui.characters.datasource.CharactersPagingSource
import com.ianarbuckle.rickmortycompose.ui.characters.repository.CharactersRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class CharactersViewModel @Inject constructor(
    private val repository: CharactersRepository,
) : ViewModel() {

    val characters: Flow<PagingData<Character>> = Pager(PagingConfig(pageSize = 20)) {
        CharactersPagingSource(repository)
    }.flow

}