package ru.mts.avpopo85.weathery.data.db.implementation.realm.weather.yandexWeather

import android.content.Context
import ru.mts.avpopo85.weathery.data.db.implementation.realm.weather.common.AbsForecastRealmService
import ru.mts.avpopo85.weathery.data.db.util.OWM_FORECAST_DEFAULT_CACHE_LIFETIME_IN_MS
import ru.mts.avpopo85.weathery.data.utils.isFresh
import ru.mts.avpopo85.weathery.utils.yandexWeather.YWForecastResponseType

class YWForecastRealmService(context: Context) :
    AbsForecastRealmService<YWForecastResponseType>(context) {

    override val List<YWForecastResponseType>.isFresh: Boolean
        get() {
            val unixtimeInMillis = this.first().dateInUnixtimeUTC

            return unixtimeInMillis.isFresh(OWM_FORECAST_DEFAULT_CACHE_LIFETIME_IN_MS)
        }

    override val List<YWForecastResponseType>.isNotFresh: Boolean
        get() = !this.isFresh

    override val responseClassType: Class<YWForecastResponseType>
            by lazy { YWForecastResponseType::class.java }

}