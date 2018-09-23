package ru.mts.avpopo85.weathery.domain.interactor.implementation.openWeatherMap

import io.reactivex.Single
import ru.mts.avpopo85.weathery.domain.interactor.base.ICurrentWeatherInteractor
import ru.mts.avpopo85.weathery.domain.mapper.base.ICurrentWeatherMapper
import ru.mts.avpopo85.weathery.domain.repository.ICurrentWeatherRepository
import ru.mts.avpopo85.weathery.utils.openWeatherMap.OWMCurrentWeatherResponseType
import ru.mts.avpopo85.weathery.utils.openWeatherMap.OWMCurrentWeatherType
import javax.inject.Inject

class OWMCurrentWeatherInteractor
@Inject constructor(
    private val currentWeatherRepository: ICurrentWeatherRepository<OWMCurrentWeatherResponseType>,
    private val currentWeatherMapper: ICurrentWeatherMapper<OWMCurrentWeatherResponseType, OWMCurrentWeatherType>
) : ICurrentWeatherInteractor<OWMCurrentWeatherType> {

    override fun getCurrentWeather(): Single<OWMCurrentWeatherType> =
        currentWeatherRepository
            .getCurrentWeather()
            .map { currentWeatherMapper.mapCurrentWeatherResponse(it) }

}