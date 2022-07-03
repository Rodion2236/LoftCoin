package com.rodion2236.loftcoin.data.api

import com.rodion2236.loftcoin.data.models.listings.Listings
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface CmcApi {

    companion object {
        const val API_KEY = "X-CMC_PRO_API_KEY"
    }

    @GET("cryptocurrency/listings/latest")
    fun listings(@Query("convert") convert: String): Observable<Listings>
}