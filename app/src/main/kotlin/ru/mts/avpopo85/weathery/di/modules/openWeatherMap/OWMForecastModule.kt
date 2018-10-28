package ru.mts.avpopo85.weathery.di.modules.openWeatherMap

import android.content.Context
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
import ru.mts.avpopo85.weathery.presentation.weather.forecast.implementation.openWeatherMap.OWMForecastPresenter
import ru.mts.avpopo85.weathery.utils.openWeatherMap.OWMForecastListResponseType
import ru.mts.avpopo85.weathery.utils.openWeatherMap.OWMForecastListType
import ru.mts.avpopo85.weathery.utils.openWeatherMap.OWMForecastType

@Module
class OWMForecastModule(private val context: Context) {

    @Provides
    @OWMForecastScope
    fun provideOWMForecastPresenter(
        interactor: IForecastInteractor<OWMForecastListType>,
        schedulerManagerModule: SchedulerManagerModule
    ): ForecastContract.Presenter<OWMForecastType> =
        OWMForecastPresenter(interactor, schedulerManagerModule)

    @Provides
    @OWMForecastScope
    fun provideOWMForecastInteractor(
        repository: IForecastRepository<OWMForecastListResponseType>,
        mapper: IForecastMapper<OWMForecastListResponseType, OWMForecastListType>
    ): IForecastInteractor<OWMForecastListType> =
        OWMForecastInteractor(repository, mapper)

    @Provides
    @OWMForecastScope
    fun provideOWMForecastMapper(): IForecastMapper<OWMForecastListResponseType, OWMForecastListType> =
        OWMForecastMapper(context)

}
