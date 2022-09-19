package com.crop2cash.fetcher.presentation.ui.exhibit_listings

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.crop2cash.fetcher.presentation.ui.exhibit_listings.components.*
import com.crop2cash.fetcher.presentation.theme.spacing
import com.crop2cash.fetcher.util.loading.CustomCircularProgressIndicator
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Destination(start = true)
fun CompanyListingsScreen(
    navigator: DestinationsNavigator,
    viewModel: ExhibitListingsViewModel = hiltViewModel()
) {
    val swipeRefreshState = rememberSwipeRefreshState(
        isRefreshing = viewModel.state.isRefreshing
    )
    val state = viewModel.state
    val context = LocalContext.current
    val comingSoon: () -> Unit = {
        Toast.makeText(context, "This feature is coming soon...", Toast.LENGTH_SHORT).show()
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionBtn {
                comingSoon.invoke()
            }
        },
        topBar = {
            TopBar(text = "Exhibits with Material Design 3")
        }
    ) { values ->
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .padding(top = MaterialTheme.spacing.extraLarge)
                    .padding(top = MaterialTheme.spacing.large)
                    .fillMaxSize()
            ) {
                SearchTextField(
                    query = state.searchQuery,
                    onValueChange = {
                        viewModel.onEvent(
                            ExhibitListingsEvent.OnSearchQueryChange(it)
                        )
                    }
                )
                Row(
                    modifier = Modifier
                        .padding(horizontal = MaterialTheme.spacing.medium)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    CustomSwitch(switchOn = viewModel.darkTheme) {
                        viewModel.onEvent(
                            ExhibitListingsEvent.OnSwitchToggleClick
                        )
                    }
                    Text(
                        modifier = Modifier.padding(horizontal = MaterialTheme.spacing.medium),
                        text = "DARK MODE: ${if (viewModel.darkTheme)"ON" else "0FF"}"
                    )
                }
                Spacer(modifier = Modifier.height(MaterialTheme.spacing.small))
                SwipeRefresh(
                    state = swipeRefreshState,
                    onRefresh = {
                        viewModel.onEvent(ExhibitListingsEvent.Refresh)
                    }
                ) {
                    LazyColumn {
                        itemsIndexed(state.exhibitList) { index, exhibit ->
                            Text(
                                text = exhibit.title, // Ex: "iPhone 5s"
                                style = MaterialTheme.typography.titleLarge,
                                modifier = Modifier
                                    .padding(
                                        vertical = MaterialTheme.spacing.small,
                                        horizontal = MaterialTheme.spacing.medium
                                    )
                            )
                            LazyRow {
                                itemsIndexed(exhibit.images) { imageIndex, imageUrl ->
                                    ImageCard(
                                        modifier = Modifier
                                            .padding(MaterialTheme.spacing.medium)
                                            .clickable {
                                                Toast
                                                    .makeText(
                                                        context,
                                                        "You just clicked on Image ${imageIndex + 1} of ${exhibit.title}",
                                                        Toast.LENGTH_SHORT
                                                    )
                                                    .show()
                                            },
                                        imageUrl = imageUrl,
                                        errorText = state.error,
                                        description = "Image number: ${imageIndex + 1}",
                                        favoriteIsClick = true,
                                        onMarkAsFavoriteClick = { comingSoon.invoke() },
                                        onShareWithOthersClick = comingSoon
                                    )
                                }
                            }
                            if (index < state.exhibitList.size) {
                                Divider(modifier = Modifier.padding(
                                    horizontal = MaterialTheme.spacing.medium
                                ))
                            }
                        }
                        item { Spacer(modifier = Modifier.padding(values)) }
                    }
                }
            }
            if (state.isLoading) {
                Box(modifier = Modifier
                    .fillMaxSize()
                    .align(Alignment.Center)
                ) {
                    CustomCircularProgressIndicator()
                }
            }
        }
    }
}
