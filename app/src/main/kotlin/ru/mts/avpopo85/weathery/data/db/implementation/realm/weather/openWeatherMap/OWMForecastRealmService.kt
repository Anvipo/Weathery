package ru.mts.avpopo85.weathery.data.db.implementation.realm.weather.openWeatherMap

import android.content.Context
import ru.mts.avpopo85.weathery.data.db.implementation.realm.weather.common.AbsForecastRealmService
import ru.mts.avpopo85.weathery.data.db.implementation.realm.weather.openWeatherMap.utils.OWM_FORECAST_DEFAULT_CACHE_LIFETIME_IN_MS
import ru.mts.avpopo85.weathery.utils.openWeatherMap.OWMListItemResponseType
import javax.inject.Inject

class OWMForecastRealmService
@Inject constructor(context: Context) :
    AbsForecastRealmService<OWMListItemResponseType>(context) {

    override val responseClassType: Class<OWMListItemResponseType>
            by lazy { OWMListItemResponseType::class.java }

    override val cacheLifeTimeInMs: Long = OWM_FORECAST_DEFAULT_CACHE_LIFETIME_IN_MS

}
