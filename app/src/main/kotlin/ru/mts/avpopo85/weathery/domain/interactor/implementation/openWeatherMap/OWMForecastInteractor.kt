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
    private val repository: IForecastRepository<OWMForecastListResponseType>,
    private val mapper: IForecastMapper<OWMForecastListResponseType, OWMForecastListType>
) : IForecastInteractor<OWMForecastListType> {

    override fun getForecast(): Single<OWMForecastListType> =
        repository
            .getForecast()
            .map { mapper.mapForecast(it) }

}
