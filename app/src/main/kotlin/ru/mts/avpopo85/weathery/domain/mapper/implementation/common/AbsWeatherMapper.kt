package ru.mts.avpopo85.weathery.domain.mapper.implementation.common

import ru.mts.avpopo85.weathery.data.db.implementation.realm.weather.utils.isFresh
import ru.mts.avpopo85.weathery.data.model.base.common.IWeatherResponse

abstract class AbsWeatherMapper<T : IWeatherResponse> {

    protected abstract val cacheLifeTimeInMs: Long

    protected val T.isFresh: Boolean
        get() {
            val unixtimeInMillis: Long = this.timeOfDataCalculationUnixUTCInSeconds * 1000

            return unixtimeInMillis.isFresh(cacheLifeTimeInMs)
        }

}