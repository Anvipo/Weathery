package ru.mts.avpopo85.weathery.di.weather

import dagger.Subcomponent
import ru.mts.avpopo85.weathery.di.global.scopes.YandexWeatherScope
import ru.mts.avpopo85.weathery.presentation.weather.yandex.YandexWeatherActivity
import ru.mts.avpopo85.weathery.presentation.weather.yandex.YandexWeatherPresenter

@YandexWeatherScope
@Subcomponent(modules = [YandexWeatherModule::class])
interface YandexWeatherSubcomponent {
    fun getPresenter(): YandexWeatherPresenter

    fun inject(yandexWeatherActivity: YandexWeatherActivity)
}