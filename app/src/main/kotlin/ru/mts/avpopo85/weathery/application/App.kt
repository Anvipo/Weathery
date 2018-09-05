package ru.mts.avpopo85.weathery.application

import android.app.Application
import ru.mts.avpopo85.weathery.data.network.YandexWeatherApiService.Companion.BASE_URL
import ru.mts.avpopo85.weathery.di.global.*

/*
    1. Recycler View
    2. API
    3. Местоположение
*/

//https://five.agency/android-architecture-part-4-applying-clean-architecture-on-android-hands-on/
class App : Application() {

    private fun initDagger() {
        appComponentForYandexWeather = DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .dataModule(DataModule(BASE_URL))
                .schedulerManagerModule(SchedulerManagerModule())
                .build()
    }

    override fun onCreate() {
        super.onCreate()

        initDagger()
    }

    companion object {
        lateinit var appComponentForYandexWeather: AppComponent
            private set
    }
}