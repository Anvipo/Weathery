package ru.mts.avpopo85.weathery.domain.interactor.implementation.yandexWeather

import io.reactivex.Single
import ru.mts.avpopo85.weathery.domain.interactor.base.IForecastInteractor
import ru.mts.avpopo85.weathery.domain.mapper.base.IForecastMapper
import ru.mts.avpopo85.weathery.domain.repository.IForecastRepository
import ru.mts.avpopo85.weathery.utils.yandexWeather.YWForecastListResponseType
import ru.mts.avpopo85.weathery.utils.yandexWeather.YWForecastListType
import javax.inject.Inject


class YWForecastInteractor
@Inject constructor(
    private val forecastRepository: IForecastRepository<YWForecastListResponseType>,
    private val forecastMapper: IForecastMapper<YWForecastListResponseType, YWForecastListType>
) : IForecastInteractor<YWForecastListType> {

    override fun getForecast(): Single<YWForecastListType> =
        forecastRepository
            .getForecast()
            .map { forecastMapper.mapForecast(it) }

}