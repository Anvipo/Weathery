package ru.mts.avpopo85.weathery.domain.model.implementation.yandexWeather.forecast

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import ru.mts.avpopo85.weathery.domain.model.base.yandexWeather.IYWParts

@Parcelize
/**Forecasts by time of day and 12-hour yandexForecasts.*/
data class YWParts(

    override val nightForecast: YWDayTime,

    override val morningForecast: YWDayTime,

    override val dayForecast: YWDayTime,

    override val eveningForecast: YWDayTime,

    override val _12HourForecastForDay: YWDayShort,

    override val _12HourForecastForNight: YWDayShort

) : Parcelable, IYWParts
