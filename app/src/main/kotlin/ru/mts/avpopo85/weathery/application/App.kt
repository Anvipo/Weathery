package ru.mts.avpopo85.weathery.application

import android.app.Application
import io.realm.Realm
import io.realm.RealmConfiguration
import ru.mts.avpopo85.weathery.data.network.YandexWeatherApiService.Companion.BASE_URL
import ru.mts.avpopo85.weathery.di.global.*


class App : Application() {

    override fun onCreate() {
        super.onCreate()

        initDagger()

        initRealm()
    }

    private fun initRealm() {
        Realm.init(this)

        val config = RealmConfiguration.Builder().name("forecasts").deleteRealmIfMigrationNeeded()

        Realm.setDefaultConfiguration(config.build()!!)
    }

    private fun initDagger() {
        appComponentForYandexWeather = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .dataModule(DataModule())
            .retrofitModule(RetrofitModule(BASE_URL))
            .schedulerManagerModule(SchedulerManagerModule())
            .build()
    }

    companion object {

        lateinit var appComponentForYandexWeather: AppComponent
            private set

    }

}