package com.ianarbuckle.rickmortycompose.api

import retrofit2.http.GET

interface RickMortyService {

    @GET("/api/character/")
    suspend fun retrieveCharacters(): Characters

    @GET("/api/location/")
    suspend fun retrieveLocations(): RickMortyLocations
}