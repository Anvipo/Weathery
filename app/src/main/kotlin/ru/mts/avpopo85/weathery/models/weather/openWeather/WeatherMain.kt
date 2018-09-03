package ru.mts.avpopo85.weathery.models.weather.openWeather

abstract class WeatherMain(
        //Temperature. Unit Default: Kelvin, Metric: Celsius, Imperial: Fahrenheit.
        open val temperatureInUnits: Double,

        //Atmospheric pressure (on the sea level, if there is no sea_level or grnd_level data), hPa
        open val atmosphericPressureInHPa: Double,

        //Humidity, %
        open val humidityInPercents: Int,

        /*
         Minimum temperature at the moment.
         This is deviation from current temp
         that is possible for large cities and megalopolises geographically expanded (use these parameter optionally).
         Unit Default: Kelvin, Metric: Celsius, Imperial: Fahrenheit.
        */
        open val minimumTemperatureInUnits: Double,

        /*
         Maximum temperature at the moment.
         This is deviation from current temp
         that is possible for large cities and megalopolises geographically expanded (use these parameter optionally).
         Unit Default: Kelvin, Metric: Celsius, Imperial: Fahrenheit.
        */
        open val maximumTemperatureInUnits: Double,

        /*
         Atmospheric pressure on the sea level, hPa
        */
        open val atmosphericPressureOnSeaLevelInHPa: Double,

        /*
         Atmospheric pressure on the ground level, hPa
        */
        open val atmosphericPressureOnGroundLevelInHPa: Double
)
