package ru.mts.avpopo85.weathery.domain.interactor.implementation

import io.reactivex.Single
import ru.mts.avpopo85.weathery.domain.interactor.base.ForecastInteractor
import ru.mts.avpopo85.weathery.domain.mapper.base.ForecastMapper
import ru.mts.avpopo85.weathery.domain.repository.ForecastRepository
import ru.mts.avpopo85.weathery.utils.ForecastListResponseType
import ru.mts.avpopo85.weathery.utils.ForecastListType
import javax.inject.Inject


class YWForecastInteractor
@Inject constructor(
    private val forecastListRepository: ForecastRepository<ForecastListResponseType>,
    private val yandexForecastListListMapper: ForecastMapper<ForecastListResponseType, ForecastListType>
) : ForecastInteractor<ForecastListType> {

    override fun getForecast(): Single<ForecastListType> =
        forecastListRepository
            .getForecast()
            .map { yandexForecastListListMapper.mapForecast(it) }

}