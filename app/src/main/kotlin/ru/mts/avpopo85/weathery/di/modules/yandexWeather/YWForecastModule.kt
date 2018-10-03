package ru.mts.avpopo85.weathery.di.modules.yandexWeather

import dagger.Module
import dagger.Provides
import ru.mts.avpopo85.weathery.di.global.SchedulerManagerModule
import ru.mts.avpopo85.weathery.di.scopes.yandexWeather.YWForecastScope
import ru.mts.avpopo85.weathery.domain.interactor.base.IForecastInteractor
import ru.mts.avpopo85.weathery.domain.interactor.implementation.yandexWeather.YWForecastInteractor
import ru.mts.avpopo85.weathery.domain.mapper.base.IForecastMapper
import ru.mts.avpopo85.weathery.domain.mapper.implementation.yandexWeather.YWForecastMapper
import ru.mts.avpopo85.weathery.domain.repository.IForecastRepository
import ru.mts.avpopo85.weathery.presentation.weather.forecast.base.ForecastContract
import ru.mts.avpopo85.weathery.presentation.weather.forecast.implementation.yandexWeather.YWForecastActivity
import ru.mts.avpopo85.weathery.presentation.weather.forecast.implementation.yandexWeather.YWForecastPresenter
import ru.mts.avpopo85.weathery.utils.yandexWeather.YWForecastListResponseType
import ru.mts.avpopo85.weathery.utils.yandexWeather.YWForecastListType

@Suppress("SpellCheckingInspection")
@Module
class YWForecastModule(private val context: YWForecastActivity) {

    @Provides
    @YWForecastScope
    fun provideYWForecastPresenter(
        interactor: IForecastInteractor<YWForecastListType>,
        schedulerManagerModule: SchedulerManagerModule
    ): ForecastContract.Presenter<YWForecastListType> =
        YWForecastPresenter(
            interactor,
            schedulerManagerModule,
            context
        )

    @Provides
    @YWForecastScope
    fun provideYWForecastInteractor(
        repository: IForecastRepository<YWForecastListResponseType>,
        mapper: IForecastMapper<YWForecastListResponseType, YWForecastListType>
    ): IForecastInteractor<YWForecastListType> =
        YWForecastInteractor(
            repository,
            mapper
        )

    @Provides
    @YWForecastScope
    fun provideYWForecastMapper(): IForecastMapper<YWForecastListResponseType, YWForecastListType> =
        YWForecastMapper(context)

}