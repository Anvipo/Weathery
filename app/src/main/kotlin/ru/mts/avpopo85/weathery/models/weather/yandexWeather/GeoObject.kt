package ru.mts.avpopo85.weathery.models.weather.yandexWeather

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class GeoObject(
        //object with information about the district of a city
        @SerializedName("district")
        @Expose
        val districtInfo: DistrictInfo?,

        //id of locality
        @SerializedName("id")
        @Expose
        val localityId: Double,

        //name of locality
        @SerializedName("name")
        @Expose
        val localityName: String,

        //object with information about the city
        @SerializedName("locality")
        @Expose
        val cityInfo: DistrictInfo?,

        //object with information about the region
        @SerializedName("province")
        @Expose
        val provinceInfo: DistrictInfo?,

        //object with information about the country
        @SerializedName("country")
        @Expose
        val countryInfo: DistrictInfo?
)
