package ru.mts.avpopo85.weathery.data.repository.weather.openWeatherMap.utils

import io.reactivex.Single

typealias TripleOfOWMApiCalls <T> =
        Triple<
                Single<T>?,
                Single<T>?,
                Single<T>?
                >