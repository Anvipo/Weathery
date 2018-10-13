package ru.mts.avpopo85.weathery.data.db.implementation.realm.weather.yandexWeather

import android.content.Context
import ru.mts.avpopo85.weathery.data.db.implementation.realm.weather.common.AbsForecastRealmService
import ru.mts.avpopo85.weathery.data.db.implementation.realm.weather.openWeatherMap.utils.OWM_FORECAST_DEFAULT_CACHE_LIFETIME_IN_MS
import ru.mts.avpopo85.weathery.data.db.implementation.realm.weather.utils.isFresh
import ru.mts.avpopo85.weathery.utils.yandexWeather.YWForecastResponseType
import javax.inject.Inject

class YWForecastRealmService
@Inject constructor(context: Context) :
    AbsForecastRealmService<YWForecastResponseType>(context) {

    override val List<YWForecastResponseType>.isFresh: Boolean
        get() {
            val unixtimeInMillis: Long = this.first().dateInUnixUTCInSeconds

            return unixtimeInMillis.isFresh(OWM_FORECAST_DEFAULT_CACHE_LIFETIME_IN_MS)
        }

    override val List<YWForecastResponseType>.isNotFresh: Boolean
        get() = !this.isFresh

    override val responseClassType: Class<YWForecastResponseType>
            by lazy { YWForecastResponseType::class.java }

}