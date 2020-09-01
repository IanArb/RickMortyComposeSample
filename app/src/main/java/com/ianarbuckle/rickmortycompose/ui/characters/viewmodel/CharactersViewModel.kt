package com.ianarbuckle.rickmortycompose.ui.characters.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ianarbuckle.rickmortycompose.ui.characters.usecase.CharactersUseCase
import com.ianarbuckle.rickmortycompose.utils.CoroutineDispatchers
import com.ianarbuckle.rickmortycompose.utils.Result
import com.ianarbuckle.rickmortycompose.utils.UIViewState
import com.ianarbuckle.rickmortycompose.utils.asLiveData
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class CharactersViewModel @ViewModelInject constructor(private val useCase: CharactersUseCase,
                                                       private val dispatchers: CoroutineDispatchers) : ViewModel() {

    private val _mutableUIViewState = MutableLiveData<UIViewState<Any>>()

    val uiViewStateObservable = _mutableUIViewState.asLiveData()

    init {
        emitUIState(UIViewState.Loading)

        viewModelScope.launch(dispatchers.io) {
            val result = useCase.fetchConferences()

            withContext(dispatchers.main) {
                if (result is Result.Success) {
                    emitUIState(UIViewState.Success(result.data))
                } else {
                    emitUIState(UIViewState.Error)
                }
            }
        }
    }

    private fun emitUIState(state: UIViewState<Any>) {
        _mutableUIViewState.postValue(state)
    }

}