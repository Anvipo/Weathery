package ru.mts.avpopo85.weathery.domain.model.base.common

import ru.mts.avpopo85.weathery.utils.common.PrecipitationType

interface IWeather {

    /**In percents*/
    val cloudiness: String

    val precipitationType: PrecipitationType

    val cityName: String

    val isFresh: Boolean

}
