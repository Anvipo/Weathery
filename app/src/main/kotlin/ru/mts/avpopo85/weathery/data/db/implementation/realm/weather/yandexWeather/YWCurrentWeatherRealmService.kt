package ru.mts.avpopo85.weathery.data.db.implementation.realm.weather.yandexWeather

import android.content.Context
import ru.mts.avpopo85.weathery.data.db.implementation.realm.weather.common.AbsCurrentWeatherRealmService
import ru.mts.avpopo85.weathery.data.db.implementation.realm.weather.yandexWeather.utils.YW_DEFAULT_CACHE_LIFETIME
import ru.mts.avpopo85.weathery.utils.yandexWeather.YWCurrentWeatherResponseType
import javax.inject.Inject

class YWCurrentWeatherRealmService
@Inject constructor(context: Context) :
    AbsCurrentWeatherRealmService<YWCurrentWeatherResponseType>(context) {

    override val responseClassType: Class<YWCurrentWeatherResponseType>
            by lazy { YWCurrentWeatherResponseType::class.java }

    //todo проверить в каких единицах измерения приходит время
    override val cacheLifeTimeInMs: Long = YW_DEFAULT_CACHE_LIFETIME

}