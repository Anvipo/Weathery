package ru.mts.avpopo85.weathery.domain.interactor.implementation.openWeatherMap

import io.reactivex.Single
import ru.mts.avpopo85.weathery.domain.interactor.base.IForecastInteractor
import ru.mts.avpopo85.weathery.domain.mapper.base.IForecastMapper
import ru.mts.avpopo85.weathery.domain.repository.IForecastRepository
import ru.mts.avpopo85.weathery.utils.openWeatherMap.OWMForecastListResponseType
import ru.mts.avpopo85.weathery.utils.openWeatherMap.OWMForecastListType
import javax.inject.Inject


class OWMForecastInteractor
@Inject constructor(
    private val forecastRepository: IForecastRepository<OWMForecastListResponseType>,
    private val forecastMapper: IForecastMapper<OWMForecastListResponseType, OWMForecastListType>
) : IForecastInteractor<OWMForecastListType> {

    override fun getForecast(): Single<OWMForecastListType> =
        forecastRepository
            .getForecast()
            .map { forecastMapper.mapForecast(it) }

}