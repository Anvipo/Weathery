package ru.mts.avpopo85.weathery.di.weather.yandexWeather

import dagger.Subcomponent
import ru.mts.avpopo85.weathery.di.global.scopes.YandexWeatherScope
import ru.mts.avpopo85.weathery.presentation.weather.yandexWeather.currentWeather.YandexCurrentWeatherActivity
import ru.mts.avpopo85.weathery.presentation.weather.yandexWeather.currentWeather.YandexCurrentWeatherPresenter
import ru.mts.avpopo85.weathery.presentation.weather.yandexWeather.forecast.YandexForecastActivity

@YandexWeatherScope
@Subcomponent(modules = [YandexWeatherModule::class])
interface YandexWeatherSubcomponent {
    fun getPresenter(): YandexCurrentWeatherPresenter

    fun inject(yandexCurrentWeatherActivity: YandexCurrentWeatherActivity)
    fun inject(yandexForecastActivity: YandexForecastActivity)
}