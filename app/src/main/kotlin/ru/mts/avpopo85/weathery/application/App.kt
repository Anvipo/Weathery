package ru.mts.avpopo85.weathery.application

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.content.getSystemService
import androidx.multidex.MultiDex
import ru.mts.avpopo85.weathery.R
import ru.mts.avpopo85.weathery.di.global.*
import ru.mts.avpopo85.weathery.di.global.DaggerAppComponent.builder
import ru.mts.avpopo85.weathery.presentation.utils.TODAY_BAD_WEATHER_CHANNEL_ID
import ru.mts.avpopo85.weathery.presentation.utils.TOMORROW_BAD_WEATHER_CHANNEL_ID
import ru.mts.avpopo85.weathery.utils.openWeatherMap.OWMConstants
import ru.mts.avpopo85.weathery.utils.yandexWeather.YWConstants

class App : Application() {

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    override fun onCreate() {
        super.onCreate()

        initDagger()
        createWeatherNotificationChannel()
    }

    private fun createWeatherNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createTodayBadWeatherChannel()
            createTomorrowBadWeatherChannel()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createTodayBadWeatherChannel() {
        val name = getString(R.string.today_bad_weather_channel_name)

        val channel = NotificationChannel(
            TODAY_BAD_WEATHER_CHANNEL_ID,
            name,
            NotificationManager.IMPORTANCE_HIGH
        )

        val notificationManager: NotificationManager = getSystemService()!!
        notificationManager.createNotificationChannel(channel)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createTomorrowBadWeatherChannel() {
        val name = getString(R.string.tomorrow_bad_weather_channel_name)

        val channel = NotificationChannel(
            TOMORROW_BAD_WEATHER_CHANNEL_ID,
            name,
            NotificationManager.IMPORTANCE_HIGH
        )

        val notificationManager: NotificationManager = getSystemService()!!
        notificationManager.createNotificationChannel(channel)
    }

    private fun initDagger() {
        appComponent = builder()
            .sharedPreferencesModule(SharedPreferencesModule(this))
            .appModule(AppModule(this))
            .networkModule(NetworkModule(this))
            .realmModule(RealmModule(this))
            .oWMRetrofitModule(OWMRetrofitModule(OWMConstants.BASE_URL))
            .yWRetrofitModule(YWRetrofitModule(YWConstants.BASE_URL))
            .build()
    }

    companion object {

        val TAG: String = App::class.java.simpleName

        lateinit var appComponent: AppComponent
            private set

    }

}
