package ru.mts.avpopo85.weathery.data.db.implementation.realm.weather.yandexWeather

import android.content.Context
import ru.mts.avpopo85.weathery.data.db.implementation.realm.weather.common.AbsForecastRealmService
import ru.mts.avpopo85.weathery.data.db.implementation.realm.weather.yandexWeather.utils.YW_DEFAULT_CACHE_LIFETIME
import ru.mts.avpopo85.weathery.utils.yandexWeather.YWForecastResponseType
import javax.inject.Inject

class YWForecastRealmService
@Inject constructor(context: Context) :
    AbsForecastRealmService<YWForecastResponseType>(context) {

    override val responseClassType: Class<YWForecastResponseType>
            by lazy { YWForecastResponseType::class.java }

    override val cacheLifeTimeInMs: Long = YW_DEFAULT_CACHE_LIFETIME

}
