package ru.mts.avpopo85.weathery.di.subcomponents.yandexWeather

import dagger.Subcomponent
import ru.mts.avpopo85.weathery.di.modules.yandexWeather.YWCurrentWeatherModule
import ru.mts.avpopo85.weathery.di.scopes.yandexWeather.YWCurrentWeatherScope
import ru.mts.avpopo85.weathery.presentation.weather.currentWeather.implementation.yandexWeather.YWCurrentWeatherActivity

@Subcomponent(modules = [YWCurrentWeatherModule::class])
@YWCurrentWeatherScope
interface YWCurrentWeatherSubcomponent {

    fun inject(ywCurrentWeatherActivity: YWCurrentWeatherActivity)

}