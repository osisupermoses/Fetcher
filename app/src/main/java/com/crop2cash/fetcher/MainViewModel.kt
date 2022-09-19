package com.crop2cash.fetcher

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.crop2cash.fetcher.data.repository.DataStoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val dataStoreRepository: DataStoreRepository
): ViewModel() {

    var darkTheme by mutableStateOf(false)
    var isLoading by mutableStateOf(true)

    init {
        getThemeState()
        viewModelScope.launch {
            delay(3000)
            isLoading = false
        }
    }

    private fun getThemeState() {
        viewModelScope.launch {
            dataStoreRepository.readThemeState().collectLatest { isDarkTheme ->
                darkTheme = isDarkTheme
            }
        }
    }
}