package ru.mts.avpopo85.weathery.domain.model.implementation.yandexWeather.forecast

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import ru.mts.avpopo85.weathery.domain.model.base.yandexWeather.IYWForecast
import ru.mts.avpopo85.weathery.utils.common.PrecipitationType

/**This object contains weather forecast data.*/
@Parcelize
data class YWForecast(

    override val cloudiness: String,

    override val precipitationType: PrecipitationType,

    override val isFresh: Boolean,

    override val cityName: String,

    override val date: String,

    override val dateInSeconds: Long,

    override val dateInUnixtime: String,

    override val weekSerialNumber: Int,

    override val sunriseInLocalTime: String?,

    override val sunsetInLocalTime: String?,

    override val moonCode: String,

    override val moonText: String,

    override val parts: YWParts,

    override val hours: List<YWHourInfo>?

) : Parcelable, IYWForecast
