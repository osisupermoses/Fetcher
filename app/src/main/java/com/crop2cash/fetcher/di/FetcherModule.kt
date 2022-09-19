package com.crop2cash.fetcher.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.crop2cash.fetcher.data.local.Converters
import com.crop2cash.fetcher.data.local.ExhibitDatabase
import com.crop2cash.fetcher.data.remote.ExhibitApi
import com.crop2cash.fetcher.data.repository.DataStoreRepository
import com.crop2cash.fetcher.data.repository.RestExhibitsLoader
import com.crop2cash.fetcher.data.util.GsonParser
import com.crop2cash.fetcher.domain.repository.ExhibitsLoader
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FetcherModule {

    @Provides
    @Singleton
    fun providesStockApi(): ExhibitApi {
        return Retrofit.Builder()
            .baseUrl(ExhibitApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create()
    }

    @Provides
    @Singleton
    fun providesExhibitDatabase(app: Application): ExhibitDatabase {
        return Room.databaseBuilder(
            app,
            ExhibitDatabase::class.java,
            "exhibit_db"
        ).addTypeConverter(Converters(GsonParser(Gson())))
            .build()
    }

    @Provides
    @Singleton
    fun providesExhibitRepository(
        api: ExhibitApi,
        db: ExhibitDatabase
    ): ExhibitsLoader {
        return RestExhibitsLoader(api, db.dao)
    }

    @Singleton
    @Provides
    fun provideDataStoreRepository(
        @ApplicationContext context: Context
    ) = DataStoreRepository(context)

}