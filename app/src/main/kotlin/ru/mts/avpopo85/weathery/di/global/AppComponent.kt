package ru.mts.avpopo85.weathery.di.global

import dagger.Component
import ru.mts.avpopo85.weathery.di.modules.common.LocationModule
import ru.mts.avpopo85.weathery.di.modules.openWeatherMap.OWMCurrentWeatherModule
import ru.mts.avpopo85.weathery.di.modules.openWeatherMap.OWMForecastModule
import ru.mts.avpopo85.weathery.di.modules.yandexWeather.YWCurrentWeatherModule
import ru.mts.avpopo85.weathery.di.modules.yandexWeather.YWForecastModule
import ru.mts.avpopo85.weathery.di.subcomponents.common.LocationSubcomponent
import ru.mts.avpopo85.weathery.di.subcomponents.openWeatherMap.OWMCurrentWeatherSubcomponent
import ru.mts.avpopo85.weathery.di.subcomponents.openWeatherMap.OWMForecastSubcomponent
import ru.mts.avpopo85.weathery.di.subcomponents.yandexWeather.YWCurrentWeatherSubcomponent
import ru.mts.avpopo85.weathery.di.subcomponents.yandexWeather.YWForecastSubcomponent
import javax.inject.Singleton

@Suppress("SpellCheckingInspection")
@Component(
    modules = [
        AppModule::class,
        SchedulerManagerModule::class,
        OWMRetrofitModule::class,
        YWRetrofitModule::class,
        NetworkModule::class,
        RealmModule::class
    ]
)
@Singleton
interface AppComponent {

    fun plus(ywCurrentWeatherModule: YWCurrentWeatherModule): YWCurrentWeatherSubcomponent

    fun plus(ywForecastModule: YWForecastModule): YWForecastSubcomponent

    fun plus(owmCurrentWeatherModule: OWMCurrentWeatherModule): OWMCurrentWeatherSubcomponent

    fun plus(owmForecastModule: OWMForecastModule): OWMForecastSubcomponent

    fun plus(locationModule: LocationModule): LocationSubcomponent

}