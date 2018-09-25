package ru.mts.avpopo85.weathery.domain.model.implementation.openWeatherMap.forecast

import ru.mts.avpopo85.weathery.domain.model.base.common.IForecast

@Suppress("SpellCheckingInspection")
//@Parcelize

/**This object contains weather forecast data.*/
data class OWMForecast(override val date: String) : IForecast

/*override val dateUTC: String,

override val dateInUnixtimeUTC: String,

override val weekSerialNumber: Int,

override val sunriseInLocalTime: String?,

override val sunsetInLocalTime: String?,

override val moonCode: String,

override val moonText: String,

override val parts: YWParts,

override val hours: List<YWHourInfo>?*/

/*: Parcelable, IYWForecast*/
