package ru.mts.avpopo85.weathery.data.db.implementation.realm.weather.openWeatherMap

import android.content.Context
import ru.mts.avpopo85.weathery.data.db.implementation.realm.weather.common.AbsForecastRealmService
import ru.mts.avpopo85.weathery.data.db.implementation.realm.weather.openWeatherMap.utils.OWM_FORECAST_DEFAULT_CACHE_LIFETIME_IN_MS
import ru.mts.avpopo85.weathery.data.db.implementation.realm.weather.utils.isFresh
import ru.mts.avpopo85.weathery.utils.openWeatherMap.OWMForecastListResponseType
import ru.mts.avpopo85.weathery.utils.openWeatherMap.OWMForecastResponseType
import javax.inject.Inject

class OWMForecastRealmService
@Inject constructor(context: Context) :
    AbsForecastRealmService<OWMForecastResponseType>(context) {

    override val responseClassType: Class<OWMForecastResponseType>
            by lazy { OWMForecastResponseType::class.java }

    override val OWMForecastListResponseType.isFresh: Boolean
        get() {
            val unixtimeInMillis: Long = this.first().dateInUnixUTCInSeconds * 1000

            return unixtimeInMillis.isFresh(OWM_FORECAST_DEFAULT_CACHE_LIFETIME_IN_MS)
        }

    override val OWMForecastListResponseType.isNotFresh: Boolean
        get() = !this.isFresh

}