package ru.mts.avpopo85.weathery.data.db.implementation.realm.weather.openWeatherMap

import android.content.Context
import ru.mts.avpopo85.weathery.data.db.implementation.realm.weather.common.AbsCurrentWeatherRealmService
import ru.mts.avpopo85.weathery.data.db.util.OWM_CURRENT_WEATHER_CACHE_LIFETIME_IN_MS
import ru.mts.avpopo85.weathery.data.db.util.isFresh
import ru.mts.avpopo85.weathery.utils.openWeatherMap.OWMCurrentWeatherResponseType
import javax.inject.Inject

class OWMCurrentWeatherRealmService
@Inject constructor(context: Context) :
    AbsCurrentWeatherRealmService<OWMCurrentWeatherResponseType>(context) {

    override val responseClassType: Class<OWMCurrentWeatherResponseType>
            by lazy { OWMCurrentWeatherResponseType::class.java }

    override val OWMCurrentWeatherResponseType.isFresh: Boolean
        get() {
            val unixtimeInMillis: Long = this.timeOfDataCalculationUnixUTCInSeconds * 1000

            return unixtimeInMillis.isFresh(OWM_CURRENT_WEATHER_CACHE_LIFETIME_IN_MS)
        }

    override val OWMCurrentWeatherResponseType.isNotFresh: Boolean
        get() = !this.isFresh

}



