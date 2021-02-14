package com.ianarbuckle.rickmortycompose.api

import retrofit2.http.GET
import retrofit2.http.Query

interface RickMortyService {

    @GET("/api/character/")
    suspend fun retrieveCharacters(@Query("page") page: Int): Characters

    @GET("/api/location/")
    suspend fun retrieveLocations(): RickMortyLocations
}