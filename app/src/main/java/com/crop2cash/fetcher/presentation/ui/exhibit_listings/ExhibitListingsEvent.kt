package com.crop2cash.fetcher.presentation.ui.exhibit_listings

sealed class ExhibitListingsEvent {
    object Refresh: ExhibitListingsEvent()
    data class OnSearchQueryChange(val query: String): ExhibitListingsEvent()
    object OnSwitchToggleClick: ExhibitListingsEvent()
}