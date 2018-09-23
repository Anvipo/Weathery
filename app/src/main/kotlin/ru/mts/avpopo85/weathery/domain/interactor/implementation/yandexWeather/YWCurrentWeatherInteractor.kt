package ru.mts.avpopo85.weathery.domain.interactor.implementation.yandexWeather

import io.reactivex.Single
import ru.mts.avpopo85.weathery.domain.interactor.base.ICurrentWeatherInteractor
import ru.mts.avpopo85.weathery.domain.mapper.base.ICurrentWeatherMapper
import ru.mts.avpopo85.weathery.domain.repository.ICurrentWeatherRepository
import ru.mts.avpopo85.weathery.utils.YWCurrentWeatherResponseType
import ru.mts.avpopo85.weathery.utils.YWCurrentWeatherType
import javax.inject.Inject

class YWCurrentWeatherInteractor
@Inject constructor(
    private val currentWeatherRepository: ICurrentWeatherRepository<YWCurrentWeatherResponseType>,
    private val currentWeatherMapper: ICurrentWeatherMapper<YWCurrentWeatherResponseType, YWCurrentWeatherType>
) : ICurrentWeatherInteractor<YWCurrentWeatherType> {

    override fun getCurrentWeather(): Single<YWCurrentWeatherType> =
        currentWeatherRepository
            .getCurrentWeather()
            .map { currentWeatherMapper.mapCurrentWeatherResponse(it) }

}