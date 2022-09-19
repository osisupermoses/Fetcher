package com.crop2cash.fetcher.data.mapper

import com.crop2cash.fetcher.data.local.ExhibitListingEntity
import com.crop2cash.fetcher.data.remote.ExhibitDto
import com.crop2cash.fetcher.domain.model.Exhibit

fun ExhibitDto.toExhibit() =
    Exhibit(
        images = images ?: emptyList(),
        title = title ?: ""
    )

fun ExhibitListingEntity.toExhibit() =
    Exhibit(
        id = id,
        images = images,
        title = title
    )

fun Exhibit.toExhibitListingEntity() =
    ExhibitListingEntity(
        id = id,
        images = images,
        title = title
    )