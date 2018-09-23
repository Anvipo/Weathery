package ru.mts.avpopo85.weathery.di.global

import dagger.Component
import ru.mts.avpopo85.weathery.di.modules.OpenWeatherMapModule
import ru.mts.avpopo85.weathery.di.modules.YandexWeatherModule
import ru.mts.avpopo85.weathery.di.subcomponents.OpenWeatherMapSubcomponent
import ru.mts.avpopo85.weathery.di.subcomponents.YandexWeatherSubcomponent
import javax.inject.Singleton

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

    fun plus(yandexWeatherModule: YandexWeatherModule): YandexWeatherSubcomponent

    fun plus(yandexWeatherModule: OpenWeatherMapModule): OpenWeatherMapSubcomponent

}