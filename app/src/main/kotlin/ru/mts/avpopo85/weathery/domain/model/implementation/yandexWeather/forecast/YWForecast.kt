package ru.mts.avpopo85.weathery.domain.model.implementation.yandexWeather.forecast

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import ru.mts.avpopo85.weathery.domain.model.base.yandexWeather.IYWForecast

@Suppress("SpellCheckingInspection")
@Parcelize

/**This object contains weather forecast data.*/
data class YWForecast(

    override val date: String,

    override val dateInUnixtime: String,

    override val weekSerialNumber: Int,

    override val sunriseInLocalTime: String?,

    override val sunsetInLocalTime: String?,

    override val moonCode: String,

    override val moonText: String,

    override val parts: YWParts,

    override val hours: List<YWHourInfo>?

) : Parcelable, IYWForecast
