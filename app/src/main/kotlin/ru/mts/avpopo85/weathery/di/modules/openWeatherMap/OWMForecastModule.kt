package ru.mts.avpopo85.weathery.di.modules.openWeatherMap

import dagger.Module
import dagger.Provides
import ru.mts.avpopo85.weathery.di.global.SchedulerManagerModule
import ru.mts.avpopo85.weathery.di.scopes.openWeatherMap.OWMForecastScope
import ru.mts.avpopo85.weathery.domain.interactor.base.IForecastInteractor
import ru.mts.avpopo85.weathery.domain.interactor.implementation.openWeatherMap.OWMForecastInteractor
import ru.mts.avpopo85.weathery.domain.mapper.base.IForecastMapper
import ru.mts.avpopo85.weathery.domain.mapper.implementation.openWeatherMap.OWMForecastMapper
import ru.mts.avpopo85.weathery.domain.repository.IForecastRepository
import ru.mts.avpopo85.weathery.presentation.weather.forecast.base.ForecastContract
import ru.mts.avpopo85.weathery.presentation.weather.forecast.implementation.openWeatherMap.OWMForecastActivity
import ru.mts.avpopo85.weathery.presentation.weather.forecast.implementation.openWeatherMap.OWMForecastPresenter
import ru.mts.avpopo85.weathery.utils.openWeatherMap.OWMForecastListResponseType
import ru.mts.avpopo85.weathery.utils.openWeatherMap.OWMForecastListType

@Module
@Suppress("SpellCheckingInspection")
class OWMForecastModule(private val context: OWMForecastActivity) {

    @Provides
    @OWMForecastScope
    fun provideOWMForecastPresenter(
        currentWeatherInteractor: IForecastInteractor<OWMForecastListType>,
        schedulerManagerModule: SchedulerManagerModule
    ): ForecastContract.Presenter<OWMForecastListType> =
        OWMForecastPresenter(
            currentWeatherInteractor,
            schedulerManagerModule,
            context
        )

    @Provides
    @OWMForecastScope
    fun provideOWMForecastInteractor(
        currentWeatherRepository: IForecastRepository<OWMForecastListResponseType>,
        currentWeatherMapper: IForecastMapper<OWMForecastListResponseType, OWMForecastListType>
    ): IForecastInteractor<OWMForecastListType> =
        OWMForecastInteractor(
            currentWeatherRepository,
            currentWeatherMapper
        )

    @Provides
    @OWMForecastScope
    fun provideOWMForecastMapper():
            IForecastMapper<OWMForecastListResponseType, OWMForecastListType> =
        OWMForecastMapper(context)

}