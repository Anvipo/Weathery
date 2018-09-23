package ru.mts.avpopo85.weathery.application

import android.app.Application
import ru.mts.avpopo85.weathery.data.network.retrofit.yandexWeather.YWConstants.BASE_URL
import ru.mts.avpopo85.weathery.di.global.*


class App : Application() {

    override fun onCreate() {
        super.onCreate()

        initDagger()
    }

    private fun initDagger() {
        appComponentForYandexWeather = DaggerAppComponent
            .builder()
            .appModule(AppModule(this))
            .dataModule(DataModule())
            .networkModule(NetworkModule(this))
            .realmModule(RealmModule(this))
            .retrofitModule(RetrofitModule(BASE_URL))
            .schedulerManagerModule(SchedulerManagerModule())
            .build()
    }

    companion object {

        lateinit var appComponentForYandexWeather: AppComponent
            private set

    }

}