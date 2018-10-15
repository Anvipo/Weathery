package ru.mts.avpopo85.weathery.di.modules.openWeatherMap

import dagger.Module
import dagger.Provides
import ru.mts.avpopo85.weathery.di.global.SchedulerManagerModule
import ru.mts.avpopo85.weathery.di.scopes.openWeatherMap.OWMCurrentWeatherScope
import ru.mts.avpopo85.weathery.domain.interactor.base.ICurrentWeatherInteractor
import ru.mts.avpopo85.weathery.domain.interactor.implementation.openWeatherMap.OWMCurrentWeatherInteractor
import ru.mts.avpopo85.weathery.domain.mapper.base.ICurrentWeatherMapper
import ru.mts.avpopo85.weathery.domain.mapper.implementation.openWeatherMap.OWMCurrentWeatherMapper
import ru.mts.avpopo85.weathery.domain.repository.ICurrentWeatherRepository
import ru.mts.avpopo85.weathery.presentation.weather.currentWeather.base.CurrentWeatherContract
import ru.mts.avpopo85.weathery.presentation.weather.currentWeather.implementation.openWeatherMap.OWMCurrentWeatherActivity
import ru.mts.avpopo85.weathery.presentation.weather.currentWeather.implementation.openWeatherMap.OWMCurrentWeatherPresenter
import ru.mts.avpopo85.weathery.utils.openWeatherMap.OWMCurrentWeatherResponseType
import ru.mts.avpopo85.weathery.utils.openWeatherMap.OWMCurrentWeatherType

@Module
class OWMCurrentWeatherModule(private val context: OWMCurrentWeatherActivity) {

    @Provides
    @OWMCurrentWeatherScope
    fun provideOWMCurrentWeatherPresenter(
        interactor: ICurrentWeatherInteractor<OWMCurrentWeatherType>,
        schedulerManagerModule: SchedulerManagerModule
    ): CurrentWeatherContract.Presenter<OWMCurrentWeatherType> =
        OWMCurrentWeatherPresenter(
            interactor,
            schedulerManagerModule
        )

    @Provides
    @OWMCurrentWeatherScope
    fun provideOWMCurrentWeatherInteractor(
        repository: ICurrentWeatherRepository<OWMCurrentWeatherResponseType>,
        mapper: ICurrentWeatherMapper<OWMCurrentWeatherResponseType, OWMCurrentWeatherType>
    ): ICurrentWeatherInteractor<OWMCurrentWeatherType> =
        OWMCurrentWeatherInteractor(
            repository,
            mapper
        )

    @Provides
    @OWMCurrentWeatherScope
    fun provideOWMCurrentWeatherMapper(): ICurrentWeatherMapper<OWMCurrentWeatherResponseType, OWMCurrentWeatherType> =
        OWMCurrentWeatherMapper(context)

}
