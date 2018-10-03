package ru.mts.avpopo85.weathery.di.subcomponents.yandexWeather

import dagger.Subcomponent
import ru.mts.avpopo85.weathery.di.modules.yandexWeather.YWForecastModule
import ru.mts.avpopo85.weathery.di.scopes.yandexWeather.YWForecastScope
import ru.mts.avpopo85.weathery.presentation.weather.forecast.implementation.yandexWeather.YWForecastActivity

@Subcomponent(modules = [YWForecastModule::class])
@YWForecastScope
interface YWForecastSubcomponent {

    fun inject(ywForecastActivity: YWForecastActivity)

}