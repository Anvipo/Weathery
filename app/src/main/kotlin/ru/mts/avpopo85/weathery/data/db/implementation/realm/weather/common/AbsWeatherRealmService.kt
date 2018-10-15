package ru.mts.avpopo85.weathery.data.db.implementation.realm.weather.common

import ru.mts.avpopo85.weathery.data.db.implementation.realm.weather.utils.isFresh
import ru.mts.avpopo85.weathery.data.model.base.common.IWeatherResponse

abstract class AbsWeatherRealmService<T : IWeatherResponse> {

    protected abstract val cacheLifeTimeInMs: Long

    protected val T.isFresh: Boolean
        get() {
            val unixtimeInMillis: Long = this.timeOfDataCalculationUnixUTCInSeconds * 1000

            return unixtimeInMillis.isFresh(cacheLifeTimeInMs)
        }

    protected val T.isNotFresh: Boolean
        get() = !this.isFresh

}