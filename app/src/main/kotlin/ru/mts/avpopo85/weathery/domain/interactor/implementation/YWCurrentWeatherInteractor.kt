package ru.mts.avpopo85.weathery.domain.interactor.implementation

import io.reactivex.Single
import ru.mts.avpopo85.weathery.domain.interactor.base.CurrentWeatherInteractor
import ru.mts.avpopo85.weathery.domain.mapper.base.CurrentWeatherMapper
import ru.mts.avpopo85.weathery.domain.repository.CurrentWeatherRepository
import ru.mts.avpopo85.weathery.utils.CurrentWeatherResponseType
import ru.mts.avpopo85.weathery.utils.CurrentWeatherType
import javax.inject.Inject

class YWCurrentWeatherInteractor
@Inject constructor(
    private val currentWeatherRepository: CurrentWeatherRepository<CurrentWeatherResponseType>,
    private val currentWeatherMapper: CurrentWeatherMapper<CurrentWeatherResponseType, CurrentWeatherType>
) : CurrentWeatherInteractor<CurrentWeatherType> {

    override fun getCurrentWeather(): Single<CurrentWeatherType> =
        currentWeatherRepository
            .getCurrentWeather()
            .map { currentWeatherMapper.mapCurrentWeatherResponse(it) }

}