package ru.mts.avpopo85.weathery.models.weather.yandex

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CurrentWeather(
        //temperature (°С)
        @SerializedName("temp")
        @Expose
        val temperature: Double,

        //what the temperature feels like (°С)
        @SerializedName("feels_like")
        @Expose
        val feelsLikeTemperature: Double,

        //the water temperature (°С)
        //this parameter is returned for localities where this information is relevant
        @SerializedName("temp_water")
        @Expose
        val waterTemperature: Double,

        //the code of the weather icon
        //the icon is available at https://yastatic/weather/i/icons/blueye/color/svg/
        // /<value from the icon field>.svg
        @SerializedName("icon")
        @Expose
        val iconId: String,

        //the code for the weather description
        @SerializedName("condition")
        @Expose
        val weatherDescription: String,

        //wind speed (meters per second)
        @SerializedName("wind_speed")
        @Expose
        val windSpeed: Double,

        //speed of wind gusts (meters per second)
        @SerializedName("wind_gust")
        @Expose
        val windGust: Double,

        //wind direction
        @SerializedName("wind_dir")
        @Expose
        val windDirection: String,

        //atmospheric pressure (mm Hg)
        @SerializedName("pressure_mm")
        @Expose
        val pressureInMmHg: Double,

        //atmospheric pressure (hPa)
        @SerializedName("pressure_pa")
        @Expose
        val pressureInhHpa: String,

        //humidity (percent)
        @SerializedName("humidity")
        @Expose
        val humidityInPercents: Double,

        //light or dark time of the day
        @SerializedName("daytime")
        @Expose
        val daytime: String,

        //polar day or polar night
        @SerializedName("polar")
        @Expose
        val polar: Boolean,

        //time of year in this locality
        @SerializedName("season")
        @Expose
        val season: String,

        //the time when weather data was measured, in Unix time
        @SerializedName("obs_time")
        @Expose
        val observationUnixTime: Double,

        //type of precipitation
        @SerializedName("prec_type")
        @Expose
        val precipitationType: Int,

        //intensity of precipitation
        @SerializedName("prec_strength")
        @Expose
        val precipitationStrength: Double,

        //cloud cover
        @SerializedName("cloudness")
        @Expose
        val cloudness: Double
)
