package com.crop2cash.fetcher.presentation.ui.exhibit_listings

import com.crop2cash.fetcher.domain.model.Exhibit

data class ExhibitListingsState(
    val isLoading: Boolean = false,
    val exhibitList: List<Exhibit> = emptyList(),
    val isRefreshing: Boolean = false,
    val searchQuery: String = "",
    val error: String = ""
)
