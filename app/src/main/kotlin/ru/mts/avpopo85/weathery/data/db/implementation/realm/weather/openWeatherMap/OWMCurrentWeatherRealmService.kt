package ru.mts.avpopo85.weathery.data.db.implementation.realm.weather.openWeatherMap

import android.content.Context
import ru.mts.avpopo85.weathery.data.db.implementation.realm.weather.common.AbsCurrentWeatherRealmService
import ru.mts.avpopo85.weathery.data.db.implementation.realm.weather.openWeatherMap.utils.OWM_CURRENT_WEATHER_CACHE_LIFETIME_IN_MS
import ru.mts.avpopo85.weathery.utils.openWeatherMap.OWMCurrentWeatherResponseType
import javax.inject.Inject

class OWMCurrentWeatherRealmService
@Inject constructor(context: Context) :
    AbsCurrentWeatherRealmService<OWMCurrentWeatherResponseType>(context) {

    override val responseClassType: Class<OWMCurrentWeatherResponseType>
            by lazy { OWMCurrentWeatherResponseType::class.java }

    override val cacheLifeTimeInMs: Long = OWM_CURRENT_WEATHER_CACHE_LIFETIME_IN_MS

}



