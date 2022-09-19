package com.crop2cash.fetcher.domain.repository

import com.crop2cash.fetcher.common.Resource
import com.crop2cash.fetcher.data.local.ExhibitListingEntity
import com.crop2cash.fetcher.domain.model.Exhibit
import kotlinx.coroutines.flow.Flow

interface ExhibitsLoader {

    suspend fun getExhibitList(
        fetchFromRemote: Boolean,
        query: String
    ): Flow<Resource<List<Exhibit>>>

    suspend fun insertExhibits(
        exhibit: List<Exhibit>
    )
}