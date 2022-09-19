package com.crop2cash.fetcher.data.repository

import com.crop2cash.fetcher.common.Resource
import com.crop2cash.fetcher.domain.model.Exhibit
import com.crop2cash.fetcher.domain.repository.ExhibitsLoader
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeRestExhibitLoader : ExhibitsLoader {

    private val exhibits = mutableListOf<Exhibit>()

    override suspend fun getExhibitList(
        fetchFromRemote: Boolean,
        query: String
    ): Flow<Resource<List<Exhibit>>> {
        return flow { emit(Resource.Success(exhibits)) }
    }

    override suspend fun insertExhibits(exhibit: List<Exhibit>) {
        exhibits.addAll(exhibits)
    }
}