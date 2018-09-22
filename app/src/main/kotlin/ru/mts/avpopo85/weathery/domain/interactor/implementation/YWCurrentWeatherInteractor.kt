package ru.mts.avpopo85.weathery.domain.interactor.implementation

import io.reactivex.Single
import ru.mts.avpopo85.weathery.domain.interactor.base.CurrentWeatherInteractor
import ru.mts.avpopo85.weathery.domain.mapper.base.CurrentWeatherMapper
import ru.mts.avpopo85.weathery.domain.repository.CurrentWeatherRepository
import ru.mts.avpopo85.weathery.utils.YWCurrentWeatherResponseType
import ru.mts.avpopo85.weathery.utils.YWCurrentWeatherType
import javax.inject.Inject

class YWCurrentWeatherInteractor
@Inject constructor(
    private val currentWeatherRepository: CurrentWeatherRepository<YWCurrentWeatherResponseType>,
    private val currentWeatherMapper: CurrentWeatherMapper<YWCurrentWeatherResponseType, YWCurrentWeatherType>
) : CurrentWeatherInteractor<YWCurrentWeatherType> {

    override fun getCurrentWeather(): Single<YWCurrentWeatherType> =
        currentWeatherRepository
            .getCurrentWeather()
            .map { currentWeatherMapper.mapCurrentWeatherResponse(it) }

}