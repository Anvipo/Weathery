package ru.mts.avpopo85.weathery.di.global

import dagger.Component
import ru.mts.avpopo85.weathery.di.modules.YandexWeatherModule
import ru.mts.avpopo85.weathery.di.subcomponents.YandexWeatherSubcomponent
import javax.inject.Singleton

@Component(
    modules = [
        AppModule::class,
        SchedulerManagerModule::class,
        DataModule::class,
        NetworkModule::class,
        RealmModule::class,
        RetrofitModule::class
    ]
)
@Singleton
interface AppComponent {

    fun plus(yandexWeatherModule: YandexWeatherModule): YandexWeatherSubcomponent

}