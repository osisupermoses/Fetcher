package com.crop2cash.fetcher.data.repository

import com.crop2cash.fetcher.common.Resource
import com.crop2cash.fetcher.data.local.ExhibitDao
import com.crop2cash.fetcher.data.local.ExhibitDatabase
import com.crop2cash.fetcher.data.mapper.toExhibit
import com.crop2cash.fetcher.data.mapper.toExhibitListingEntity
import com.crop2cash.fetcher.data.remote.ExhibitApi
import com.crop2cash.fetcher.domain.model.Exhibit
import com.crop2cash.fetcher.domain.repository.ExhibitsLoader
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

class RestExhibitsLoader(
    private val api: ExhibitApi,
    private val dao: ExhibitDao
) : ExhibitsLoader {

    override suspend fun getExhibitList(
        fetchFromRemote: Boolean,
        query: String
    ): Flow<Resource<List<Exhibit>>> = flow {
        emit(Resource.Loading(true))
        val localListings = dao.searchExhibitListing(query)
        emit(Resource.Success(
            data = localListings.map { it.toExhibit() }
        ))

        val isDbEmpty = localListings.isEmpty() && query.isBlank()
        val shouldJustLoadFromCache = !isDbEmpty && !fetchFromRemote
        if (shouldJustLoadFromCache) {
            emit(Resource.Loading(false))
            return@flow
        }
        val remoteListings = try {
            api.getExhibits().map { it.toExhibit() }
        } catch (e: IOException) {
            e.printStackTrace()
            emit(Resource.Error(e.message!!))
            null
        } catch (e: HttpException) {
            e.printStackTrace()
            emit(Resource.Error(e.message!!))
            null
        }

        remoteListings?.let { listings ->
            dao.clearExhibitListings()
            dao.insertExhibitListings(
                listings.map { it.toExhibitListingEntity() }
            )
            emit(Resource.Success(
                data = dao
                    .searchExhibitListing("")
                    .map { it.toExhibit() }
            ))
            emit(Resource.Loading(false))
        }
    }

    override suspend fun insertExhibits(exhibit: List<Exhibit>) {
        return dao.insertExhibitListings(exhibit.map { it.toExhibitListingEntity() })
    }
}