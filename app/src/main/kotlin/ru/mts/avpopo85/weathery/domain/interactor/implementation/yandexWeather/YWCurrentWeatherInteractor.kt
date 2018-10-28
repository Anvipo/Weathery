package ru.mts.avpopo85.weathery.domain.interactor.implementation.yandexWeather

import io.reactivex.Single
import ru.mts.avpopo85.weathery.domain.interactor.base.ICurrentWeatherInteractor
import ru.mts.avpopo85.weathery.domain.mapper.base.ICurrentWeatherMapper
import ru.mts.avpopo85.weathery.domain.repository.ICurrentWeatherRepository
import ru.mts.avpopo85.weathery.utils.yandexWeather.YWCurrentWeatherResponseType
import ru.mts.avpopo85.weathery.utils.yandexWeather.YWCurrentWeatherType
import javax.inject.Inject

class YWCurrentWeatherInteractor
@Inject constructor(
    private val repository: ICurrentWeatherRepository<YWCurrentWeatherResponseType>,
    private val mapper: ICurrentWeatherMapper<YWCurrentWeatherResponseType, YWCurrentWeatherType>
) : ICurrentWeatherInteractor<YWCurrentWeatherType> {

    override fun onNewLocation(): Single<YWCurrentWeatherType> {
        TODO("not implemented")
    }

    override fun getCurrentWeather(): Single<YWCurrentWeatherType> =
        repository
            .getCurrentWeather()
            .map { mapper.mapCurrentWeatherResponse(it) }

}
