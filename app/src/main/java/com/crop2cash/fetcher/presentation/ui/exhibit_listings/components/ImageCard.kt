package com.crop2cash.fetcher.presentation.ui.exhibit_listings.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.*
import com.crop2cash.fetcher.presentation.theme.GoldColor
import com.crop2cash.fetcher.presentation.theme.spacing
import com.google.accompanist.flowlayout.FlowRow
import com.google.accompanist.flowlayout.SizeMode

@ExperimentalMaterial3Api
@Composable
fun ImageCard(
    modifier: Modifier = Modifier,
    errorText: String?,
    imageUrl: String,
    description: String,
    favoriteIsClick: Boolean = false,
    onMarkAsFavoriteClick: () -> Unit,
    onShareWithOthersClick: () -> Unit
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
        ),
        shape = MaterialTheme.shapes.large
    ) {
        SubcomposeAsyncImage(
            model = imageUrl,
            contentDescription = null,
            modifier = Modifier
                .height(250.dp)
                .clip(MaterialTheme.shapes.large)
                .aspectRatio(3f / 2f),
            contentScale = ContentScale.FillBounds
        ) {
            when (painter.state) {
                is AsyncImagePainter.State.Loading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(
                            Modifier.size(MaterialTheme.spacing.extraSmall)
                        )
                    }
                }
                is AsyncImagePainter.State.Error -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize(),
                        contentAlignment = Alignment.Center,
                    ) {
                        Box(modifier = Modifier
                            .wrapContentSize()
                            .clip(MaterialTheme.shapes.medium)
                            .border(
                                width = 1.dp,
                                color = MaterialTheme.colorScheme.error,
                                shape = MaterialTheme.shapes.medium
                            )
                            .background(MaterialTheme.colorScheme.primary)
                        ) {
                            Text(
                                text = errorText!!.ifBlank { "Error loading image" },
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.onPrimary,
                                modifier = Modifier
                                    .padding(MaterialTheme.spacing.small)
                                    .align(Alignment.Center)
                            )
                        }
                    }
                }
                else -> {
                    SubcomposeAsyncImageContent()
                }
            }
        }
        Column(
            modifier = Modifier.padding(MaterialTheme.spacing.medium)
        ) {
            Text(
                text = description,
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(MaterialTheme.spacing.small))
            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                mainAxisSpacing = MaterialTheme.spacing.small,
                mainAxisSize = SizeMode.Wrap
            ) {
                AssistChip(
                    onClick = onMarkAsFavoriteClick,
                    colors = AssistChipDefaults.assistChipColors(
                        leadingIconContentColor = MaterialTheme.colorScheme.onSurfaceVariant
                    ),
                    leadingIcon = {
                        (if (favoriteIsClick) GoldColor else null)?.let {
                            Icon(
                                imageVector = Icons.Outlined.FavoriteBorder,
                                contentDescription = null,
                                tint = it
                            )
                        }
                    },
                    label = {
                        Text(text = "Mark as favorite")
                    }
                )
                AssistChip(
                    onClick = onShareWithOthersClick,
                    colors = AssistChipDefaults.assistChipColors(
                        leadingIconContentColor = MaterialTheme.colorScheme.onSurfaceVariant
                    ),
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Outlined.Share,
                            contentDescription = null
                        )
                    },
                    label = {
                        Text(text = "Share with others")
                    }
                )
            }
        }
    }
}