package ru.mts.avpopo85.weathery.di.modules.yandexWeather

import dagger.Module
import dagger.Provides
import ru.mts.avpopo85.weathery.di.global.SchedulerManagerModule
import ru.mts.avpopo85.weathery.di.scopes.yandexWeather.YWCurrentWeatherScope
import ru.mts.avpopo85.weathery.domain.interactor.base.ICurrentWeatherInteractor
import ru.mts.avpopo85.weathery.domain.interactor.implementation.yandexWeather.YWCurrentWeatherInteractor
import ru.mts.avpopo85.weathery.domain.mapper.base.ICurrentWeatherMapper
import ru.mts.avpopo85.weathery.domain.mapper.implementation.yandexWeather.YWCurrentWeatherMapper
import ru.mts.avpopo85.weathery.domain.repository.ICurrentWeatherRepository
import ru.mts.avpopo85.weathery.presentation.weather.currentWeather.base.CurrentWeatherContract
import ru.mts.avpopo85.weathery.presentation.weather.currentWeather.implementation.yandexWeather.YWCurrentWeatherActivity
import ru.mts.avpopo85.weathery.presentation.weather.currentWeather.implementation.yandexWeather.YWCurrentWeatherPresenter
import ru.mts.avpopo85.weathery.utils.yandexWeather.YWCurrentWeatherResponseType
import ru.mts.avpopo85.weathery.utils.yandexWeather.YWCurrentWeatherType

@Module
class YWCurrentWeatherModule(private val context: YWCurrentWeatherActivity) {

    @Provides
    @YWCurrentWeatherScope
    fun provideYWCurrentWeatherPresenter(
        interactor: ICurrentWeatherInteractor<YWCurrentWeatherType>,
        schedulerManagerModule: SchedulerManagerModule
    ): CurrentWeatherContract.Presenter<YWCurrentWeatherType> =
        YWCurrentWeatherPresenter(
            interactor,
            schedulerManagerModule
        )

    @Provides
    @YWCurrentWeatherScope
    fun provideYWCurrentWeatherInteractor(
        repository: ICurrentWeatherRepository<YWCurrentWeatherResponseType>,
        mapper: ICurrentWeatherMapper<YWCurrentWeatherResponseType, YWCurrentWeatherType>
    ): ICurrentWeatherInteractor<YWCurrentWeatherType> =
        YWCurrentWeatherInteractor(
            repository,
            mapper
        )

    @Provides
    @YWCurrentWeatherScope
    fun provideYWCurrentWeatherMapper(): ICurrentWeatherMapper<YWCurrentWeatherResponseType, YWCurrentWeatherType> =
        YWCurrentWeatherMapper(context)

}