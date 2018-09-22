package ru.mts.avpopo85.weathery.domain.interactor.implementation

import io.reactivex.Single
import ru.mts.avpopo85.weathery.domain.interactor.base.ForecastInteractor
import ru.mts.avpopo85.weathery.domain.mapper.base.ForecastMapper
import ru.mts.avpopo85.weathery.domain.repository.ForecastRepository
import ru.mts.avpopo85.weathery.utils.YWForecastResponseType
import ru.mts.avpopo85.weathery.utils.YWForecastType
import javax.inject.Inject


class YWForecastInteractor
@Inject constructor(
    private val forecastRepository: ForecastRepository<YWForecastResponseType>,
    private val yandexForecastMapper: ForecastMapper<YWForecastResponseType, YWForecastType>
) : ForecastInteractor<YWForecastType> {

    override fun getForecast(): Single<YWForecastType> =
        forecastRepository
            .getForecast()
            .map { yandexForecastMapper.mapForecast(it) }

}