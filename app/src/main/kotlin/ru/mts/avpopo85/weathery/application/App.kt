package ru.mts.avpopo85.weathery.application

import android.app.Application
import android.content.Context
import android.support.multidex.MultiDex
import ru.mts.avpopo85.weathery.data.utils.openWeatherMap.OWMConstants
import ru.mts.avpopo85.weathery.data.utils.yandexWeather.YWConstants
import ru.mts.avpopo85.weathery.di.global.*


class App : Application() {

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    override fun onCreate() {
        super.onCreate()

        initDagger()
    }

    private fun initDagger() {
        appComponent = DaggerAppComponent
            .builder()
            .appModule(AppModule(this))
            .networkModule(NetworkModule(this))
            .realmModule(RealmModule(this))
            .oWMRetrofitModule(OWMRetrofitModule(OWMConstants.BASE_URL))
            .yWRetrofitModule(YWRetrofitModule(YWConstants.BASE_URL))
            .build()
    }

    companion object {

        lateinit var appComponent: AppComponent
            private set

    }

}