package ru.mts.avpopo85.weathery.data.network.retrofit.location

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query
import ru.mts.avpopo85.weathery.data.model.implementation.common.googleGeocode.GoogleGeocoderResponse

interface IGoogleGeocoderApiService {

    @GET("maps/api/geocode/json")
    fun geocodeByLatLng(
        @Query("latlng")
        latlng: String,

        @Query("key")
        key: String
    ): Single<GoogleGeocoderResponse>

}