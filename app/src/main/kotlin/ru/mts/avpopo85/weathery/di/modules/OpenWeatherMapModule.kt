package ru.mts.avpopo85.weathery.di.modules

import android.content.Context
import dagger.Module
import dagger.Provides
import ru.mts.avpopo85.weathery.di.global.SchedulerManagerModule
import ru.mts.avpopo85.weathery.di.scopes.OpenWeatherMapScope
import ru.mts.avpopo85.weathery.domain.interactor.base.ICurrentWeatherInteractor
import ru.mts.avpopo85.weathery.domain.interactor.implementation.openWeatherMap.OWMCurrentWeatherInteractor
import ru.mts.avpopo85.weathery.domain.mapper.base.ICurrentWeatherMapper
import ru.mts.avpopo85.weathery.domain.mapper.implementation.openWeatherMap.OWMCurrentWeatherMapper
import ru.mts.avpopo85.weathery.domain.repository.ICurrentWeatherRepository
import ru.mts.avpopo85.weathery.presentation.weather.currentWeather.base.CurrentWeatherContract
import ru.mts.avpopo85.weathery.presentation.weather.currentWeather.implementation.openWeatherMap.OWMCurrentWeatherPresenter
import ru.mts.avpopo85.weathery.utils.openWeatherMap.OWMCurrentWeatherResponseType
import ru.mts.avpopo85.weathery.utils.openWeatherMap.OWMCurrentWeatherType

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
    fun provideOWMCurrentWeatherMapper():
            ICurrentWeatherMapper<OWMCurrentWeatherResponseType, OWMCurrentWeatherType> =
        OWMCurrentWeatherMapper()

}