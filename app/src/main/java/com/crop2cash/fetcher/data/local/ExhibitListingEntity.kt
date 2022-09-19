package com.crop2cash.fetcher.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.crop2cash.fetcher.domain.model.Exhibit

@Entity
data class ExhibitListingEntity(
    val title: String,
    val images: List<String>,
    @PrimaryKey val id: Int? = null
)
