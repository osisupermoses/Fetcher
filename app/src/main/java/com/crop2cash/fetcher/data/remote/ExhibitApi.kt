package com.crop2cash.fetcher.data.remote

import retrofit2.http.GET

interface ExhibitApi {

    @GET("Reyst/exhibit_db/list")
    suspend fun getExhibits() : List<ExhibitDto>

    companion object {
        const val BASE_URL = "https://my-json-server.typicode.com/"
    }
}