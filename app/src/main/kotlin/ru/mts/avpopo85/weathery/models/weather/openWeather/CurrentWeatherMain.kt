package ru.mts.avpopo85.weathery.models.weather.openWeather

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CurrentWeatherMain(
        //Temperature. Unit Default: Kelvin, Metric: Celsius, Imperial: Fahrenheit.
        @SerializedName("temp")
        @Expose
        override val temperatureInUnits: Double,

        //Atmospheric pressure (on the sea level, if there is no sea_level or grnd_level data), hPa
        @SerializedName("pressure")
        @Expose
        override val atmosphericPressureInHPa: Double,

        //Humidity, %
        @SerializedName("humidity")
        @Expose
        override val humidityInPercents: Int,

        /*
         Minimum temperature at the moment.
         This is deviation from current temp
         that is possible for large cities and megalopolises geographically expanded (use these parameter optionally).
         Unit Default: Kelvin, Metric: Celsius, Imperial: Fahrenheit.
        */
        @SerializedName("temp_min")
        @Expose
        override val minimumTemperatureInUnits: Double,

        /*
         Maximum temperature at the moment.
         This is deviation from current temp
         that is possible for large cities and megalopolises geographically expanded (use these parameter optionally).
         Unit Default: Kelvin, Metric: Celsius, Imperial: Fahrenheit.
        */
        @SerializedName("temp_max")
        @Expose
        override val maximumTemperatureInUnits: Double,

        /*
         Atmospheric pressure on the sea level, hPa
        */
        @SerializedName("sea_level")
        @Expose
        override val atmosphericPressureOnSeaLevelInHPa: Double,

        /*
         Atmospheric pressure on the ground level, hPa
        */
        @SerializedName("grnd_level")
        @Expose
        override val atmosphericPressureOnGroundLevelInHPa: Double
) : WeatherMain(
        temperatureInUnits,
        atmosphericPressureInHPa,
        humidityInPercents,
        minimumTemperatureInUnits,
        maximumTemperatureInUnits,
        atmosphericPressureOnSeaLevelInHPa,
        atmosphericPressureOnGroundLevelInHPa
)

