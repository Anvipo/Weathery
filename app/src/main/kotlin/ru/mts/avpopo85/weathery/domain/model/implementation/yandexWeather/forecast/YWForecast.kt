package ru.mts.avpopo85.weathery.domain.model.implementation.yandexWeather.forecast

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import ru.mts.avpopo85.weathery.domain.model.base.yandexWeather.IYWForecast

/**This object contains weather forecast data.*/
@Parcelize
data class YWForecast(

    override val isFresh: Boolean,

    override val cityName: String,

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
