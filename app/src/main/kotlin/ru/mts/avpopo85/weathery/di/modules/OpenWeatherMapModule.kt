package ru.mts.avpopo85.weathery.di.modules

import android.content.Context
import dagger.Module
import dagger.Provides
import ru.mts.avpopo85.weathery.di.global.SchedulerManagerModule
import ru.mts.avpopo85.weathery.di.scopes.OpenWeatherMapScope
import ru.mts.avpopo85.weathery.domain.interactor.base.ICurrentWeatherInteractor
import ru.mts.avpopo85.weathery.domain.interactor.base.IForecastInteractor
import ru.mts.avpopo85.weathery.domain.interactor.implementation.openWeatherMap.OWMCurrentWeatherInteractor
import ru.mts.avpopo85.weathery.domain.interactor.implementation.openWeatherMap.OWMForecastInteractor
import ru.mts.avpopo85.weathery.domain.mapper.base.ICurrentWeatherMapper
import ru.mts.avpopo85.weathery.domain.mapper.base.IForecastMapper
import ru.mts.avpopo85.weathery.domain.mapper.implementation.openWeatherMap.OWMCurrentWeatherMapper
import ru.mts.avpopo85.weathery.domain.mapper.implementation.openWeatherMap.OWMForecastMapper
import ru.mts.avpopo85.weathery.domain.repository.ICurrentWeatherRepository
import ru.mts.avpopo85.weathery.domain.repository.IForecastRepository
import ru.mts.avpopo85.weathery.presentation.weather.currentWeather.base.CurrentWeatherContract
import ru.mts.avpopo85.weathery.presentation.weather.currentWeather.implementation.openWeatherMap.OWMCurrentWeatherPresenter
import ru.mts.avpopo85.weathery.presentation.weather.forecast.base.ForecastContract
import ru.mts.avpopo85.weathery.presentation.weather.forecast.implementation.openWeatherMap.OWMForecastPresenter
import ru.mts.avpopo85.weathery.utils.openWeatherMap.OWMCurrentWeatherResponseType
import ru.mts.avpopo85.weathery.utils.openWeatherMap.OWMCurrentWeatherType
import ru.mts.avpopo85.weathery.utils.openWeatherMap.OWMForecastListResponseType
import ru.mts.avpopo85.weathery.utils.openWeatherMap.OWMForecastListType

@Module
class OpenWeatherMapModule {

    @Provides
    @OpenWeatherMapScope
    fun provideOWMCurrentWeatherPresenter(
        currentWeatherInteractor: ICurrentWeatherInteractor<OWMCurrentWeatherType>,
        schedulerManagerModule: SchedulerManagerModule,
        context: Context
    ): CurrentWeatherContract.Presenter<OWMCurrentWeatherType> =
        OWMCurrentWeatherPresenter(
            currentWeatherInteractor,
            schedulerManagerModule,
            context
        )

    @Provides
    @OpenWeatherMapScope
    fun provideOWMCurrentWeatherInteractor(
        currentWeatherRepository: ICurrentWeatherRepository<OWMCurrentWeatherResponseType>,
        currentWeatherMapper: ICurrentWeatherMapper<OWMCurrentWeatherResponseType, OWMCurrentWeatherType>
    ): ICurrentWeatherInteractor<OWMCurrentWeatherType> =
        OWMCurrentWeatherInteractor(
            currentWeatherRepository,
            currentWeatherMapper
        )

    @Provides
    @OpenWeatherMapScope
    fun provideOWMCurrentWeatherMapper(context: Context):
            ICurrentWeatherMapper<OWMCurrentWeatherResponseType, OWMCurrentWeatherType> =
        OWMCurrentWeatherMapper(context)

    @Provides
    @OpenWeatherMapScope
    fun provideOWMForecastPresenter(
        currentWeatherInteractor: IForecastInteractor<OWMForecastListType>,
        schedulerManagerModule: SchedulerManagerModule,
        context: Context
    ): ForecastContract.Presenter<OWMForecastListType> =
        OWMForecastPresenter(
            currentWeatherInteractor,
            schedulerManagerModule,
            context
        )

    @Provides
    @OpenWeatherMapScope
    fun provideOWMForecastInteractor(
        currentWeatherRepository: IForecastRepository<OWMForecastListResponseType>,
        currentWeatherMapper: IForecastMapper<OWMForecastListResponseType, OWMForecastListType>
    ): IForecastInteractor<OWMForecastListType> =
        OWMForecastInteractor(
            currentWeatherRepository,
            currentWeatherMapper
        )

    @Provides
    @OpenWeatherMapScope
    fun provideOWMForecastMapper(context: Context):
            IForecastMapper<OWMForecastListResponseType, OWMForecastListType> =
        OWMForecastMapper(context)

}