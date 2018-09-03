package ru.mts.avpopo85.weathery.models.weather.yandex

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class DistrictInfo(
        //идентификатор населённого пункта
        @SerializedName("id")
        @Expose
        val localityId: Double,

        //название населённого пункта
        @SerializedName("name")
        @Expose
        val localityName: String
)
