package ru.mts.avpopo85.weathery.di.subcomponents

import dagger.Subcomponent
import ru.mts.avpopo85.weathery.di.modules.YandexWeatherModule
import ru.mts.avpopo85.weathery.di.scopes.YandexWeatherScope
import ru.mts.avpopo85.weathery.presentation.weather.currentWeather.implementation.yandexWeather.YWCurrentWeatherActivity
import ru.mts.avpopo85.weathery.presentation.weather.forecast.yandexWeather.implementation.YWForecastActivity

@Subcomponent(modules = [YandexWeatherModule::class])
@YandexWeatherScope
interface YandexWeatherSubcomponent {

    fun inject(ywCurrentWeatherActivity: YWCurrentWeatherActivity)

    fun inject(ywForecastActivity: YWForecastActivity)

}