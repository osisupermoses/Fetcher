package com.crop2cash.fetcher.presentation.ui.exhibit_listings

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.crop2cash.fetcher.common.Resource
import com.crop2cash.fetcher.data.pref.PreferencesKeys
import com.crop2cash.fetcher.data.repository.DataStoreRepository
import com.crop2cash.fetcher.domain.repository.ExhibitsLoader
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExhibitListingsViewModel @Inject constructor(
    private val exhibitsLoader: ExhibitsLoader,
    private val dataStoreRepository: DataStoreRepository
) : ViewModel() {

    var state by mutableStateOf(ExhibitListingsState())
    private var searchJob: Job? = null
    var darkTheme by mutableStateOf(false)
    var imageId by mutableStateOf("")
    var isFavorite by mutableStateOf(false)
    var exhibitIndex: Int? by mutableStateOf(null)

    val listOfFavs: ArrayList<String> = arrayListOf()

    init {
        getExhibitListings()
        getThemeState()
    }

    // Events Exposed To The UI
    fun onEvent(event: ExhibitListingsEvent) {
        when(event) {
            is ExhibitListingsEvent.Refresh -> {
                getExhibitListings(fetchFromRemote = true)
            }
            is ExhibitListingsEvent.OnSearchQueryChange -> {
                state = state.copy(searchQuery = event.query)
                searchJob?.cancel()
                searchJob = viewModelScope.launch {
                    delay(500L)
                    getExhibitListings()
                }
            }
            is ExhibitListingsEvent.OnSwitchToggleClick -> {
                darkTheme = !darkTheme
                saveThemeState(darkTheme)
            }
        }
    }

    // Gets List of Exhibits From The Repository
    private fun getExhibitListings(
        query: String = state.searchQuery.lowercase(),
        fetchFromRemote: Boolean = false
    ) {
        viewModelScope.launch {
            exhibitsLoader
                .getExhibitList(fetchFromRemote, query)
                .collect { result ->
                    when(result) {
                        is Resource.Success -> {
                            result.data?.let { listings ->
                                state = state.copy(
                                    exhibitList = listings
                                )
                            }
                        }
                        is Resource.Error -> {
                            state = state.copy(error = result.message.toString())
                        }
                        is Resource.Loading -> {
                            state = state.copy(isLoading = result.isLoading)
                        }
                    }
                }
        }
    }

    // Saves theme state to datastore pref
    private fun saveThemeState(isDarkTheme: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreRepository.saveThemeState(isDarkTheme = isDarkTheme)
        }
    }

    // Fetches saved state from datastore pref
    private fun getThemeState() {
        viewModelScope.launch {
            dataStoreRepository.readThemeState().collectLatest { isDarkTheme ->
                darkTheme = isDarkTheme
            }
        }
    }
}