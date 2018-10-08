package ru.mts.avpopo85.weathery.data.db.implementation.realm.weather.yandexWeather

import android.content.Context
import ru.mts.avpopo85.weathery.data.db.implementation.realm.weather.common.AbsCurrentWeatherRealmService
import ru.mts.avpopo85.weathery.data.db.util.YW_DEFAULT_CACHE_LIFETIME
import ru.mts.avpopo85.weathery.data.utils.isFresh
import ru.mts.avpopo85.weathery.utils.yandexWeather.YWCurrentWeatherResponseType

class YWCurrentWeatherRealmService(context: Context) :
    AbsCurrentWeatherRealmService<YWCurrentWeatherResponseType>(context) {

    override val responseClassType: Class<YWCurrentWeatherResponseType>
            by lazy { YWCurrentWeatherResponseType::class.java }

    override val YWCurrentWeatherResponseType.isFresh: Boolean
        get() {
            val unixtimeInMillis = this.observationUnixTime * 1000

            return unixtimeInMillis.isFresh(YW_DEFAULT_CACHE_LIFETIME)
        }

    override val YWCurrentWeatherResponseType.isNotFresh: Boolean
        get() = !this.isFresh

}