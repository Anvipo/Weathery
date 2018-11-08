package ru.mts.avpopo85.weathery.presentation.weather.forecast.base

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.edit
import androidx.preference.PreferenceManager.getDefaultSharedPreferences
import ru.mts.avpopo85.weathery.R
import ru.mts.avpopo85.weathery.domain.mapper.implementation.utils.toTime
import ru.mts.avpopo85.weathery.domain.model.base.common.ICurrentWeather
import ru.mts.avpopo85.weathery.domain.model.base.common.IForecast
import ru.mts.avpopo85.weathery.presentation.utils.*
import ru.mts.avpopo85.weathery.presentation.weather.tab.TabbedWeatherActivity
import ru.mts.avpopo85.weathery.utils.common.PrecipitationType.*
import java.util.Calendar
import java.util.Date


object ForecastNotificationHelper {

    fun <T : ICurrentWeather> checkCurrentWeatherPrecipitations(
        currentWeather: T,
        context: Context
    ) {
        context.checkCurrentWeather(currentWeather = currentWeather)
    }

    fun <T : IForecast> checkPrecipitations(
        forecast: List<T>,
        context: Context
    ) {
        context.checkTodayPrecipitation(todayForecast = forecast.todayForecast)
        context.checkTomorrowPrecipitation(tomorrowForecast = forecast.tommorowForecast)
    }

    private val Context.badTodayWeatherNotificationWasShownKey: String
        get() = getString(R.string.bad_today_weather_notification_was_shown)

    private fun <T : IForecast> Context.checkTodayPrecipitation(todayForecast: List<T>) {
        val badTodayWeatherNotificationWasShown =
            sharedPreferences.getBoolean(badTodayWeatherNotificationWasShownKey, false)

        val todayMorningBeginTime = 6
        val todayMorningEndTime = 9

        if (badTodayWeatherNotificationWasShown) {
            //todo mb
            val timeToClearTodayBadWeatherNotification =
                currentHour < todayMorningBeginTime || currentHour > todayMorningEndTime

            if (timeToClearTodayBadWeatherNotification) {
                sharedPreferences.edit {
                    putBoolean(badTodayWeatherNotificationWasShownKey, false)
                }
            }
        } else {
            //todo mb
            val timeToShowTodayBadWeatherNotification =
                currentHour in todayMorningBeginTime..todayMorningEndTime

            if (timeToShowTodayBadWeatherNotification) {
                onTimeToShowTodayBadWeatherNotification(todayForecast = todayForecast)
            }
        }
    }

    private fun <T : IForecast> Context.onTimeToShowTodayBadWeatherNotification(todayForecast: List<T>) {
        val badWeather = todayForecast.filter { it.isBadWeather }

        if (badWeather.isEmpty()) {
            return
        }

        val firstBadHour = badWeather.firstOrNull { it.cloudiness.toDouble() > 50.0 } ?: return

        val intent = Intent(this, TabbedWeatherActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val pendingIntent = PendingIntent.getActivity(
            this,
            WEATHER_NOTIFICATION_REQUEST_CODE,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        val at = getString(R.string.at)

        val timeForecast = firstBadHour.dateInSeconds.toTime()

        val contentText =
            "$at $timeForecast ${getString(R.string.expected)} ${firstBadHour.weatherDescription}"

        val contentTitle = getString(R.string.today_bad_weather_expected)

        val notification = NotificationCompat.Builder(this, TODAY_BAD_WEATHER_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(contentTitle)
            .setContentText(contentText)
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .setCategory(NotificationCompat.CATEGORY_REMINDER)
            .build()

        sharedPreferences.edit {
            putBoolean(
                this@onTimeToShowTodayBadWeatherNotification.badTomorrowWeatherNotificationWasShownKey,
                true
            )
        }

        with(NotificationManagerCompat.from(this)) {
            notify(TODAY_BAD_WEATHER_IS_COMING_NOTIFICATION_ID, notification)
        }
    }

    private val <T : IForecast> List<T>.todayForecast
        get(): List<T> = this.filter { it.isTodayForecast }

    private val <T : IForecast> T.isTodayForecast: Boolean
        get() = this.differenceInDays == 0

    private fun <T : IForecast> Context.checkTomorrowPrecipitation(tomorrowForecast: List<T>) {
        val badTomorrowWeatherNotificationWasShown =
            sharedPreferences.getBoolean(badTomorrowWeatherNotificationWasShownKey, false)

        val todayEveningBeginTime = 19
        val todayEveningEndTime = 23

        if (badTomorrowWeatherNotificationWasShown) {
            //todo mb
            val timeToReset =
                currentHour > todayEveningEndTime || currentHour < todayEveningBeginTime

            if (timeToReset) {
                sharedPreferences.edit {
                    putBoolean(
                        badTomorrowWeatherNotificationWasShownKey,
                        false
                    )
                }
            }
        } else {
            //todo mb
            val timeToShowTomorrowBadWeatherNotification =
                currentHour in todayEveningBeginTime..todayEveningEndTime

            if (timeToShowTomorrowBadWeatherNotification) {
                onTimeToShowTomorrowBadWeatherNotification(tomorrowForecast)
            }
        }
    }

    private fun <T : IForecast> Context.onTimeToShowTomorrowBadWeatherNotification(tomorrowForecast: List<T>) {
        val badWeather = tomorrowForecast.filter { it.isBadWeather }

        if (badWeather.isEmpty()) {
            return
        }

        val firstBadHour = badWeather.firstOrNull { it.cloudiness.toDouble() > 50.0 } ?: return

        val intent = Intent(this, TabbedWeatherActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val pendingIntent = PendingIntent.getActivity(
            this,
            WEATHER_NOTIFICATION_REQUEST_CODE,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        val contentText =
            "${firstBadHour.date} ${getString(R.string.expected)} ${firstBadHour.weatherDescription}"

        val contentTitle = getString(R.string.tomorrow_bad_weather_expected)

        val notification = NotificationCompat.Builder(this, TOMORROW_BAD_WEATHER_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(contentTitle)
            .setContentText(contentText)
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .setCategory(NotificationCompat.CATEGORY_REMINDER)
            .build()

        sharedPreferences.edit {
            putBoolean(badTomorrowWeatherNotificationWasShownKey, true)
        }

        with(NotificationManagerCompat.from(this)) {
            notify(TOMORROW_BAD_WEATHER_IS_COMING_NOTIFICATION_ID, notification)
        }
    }

    private val <T : IForecast> List<T>.tommorowForecast
        get(): List<T> = filter { it.isTomorrowForecast }

    private val <T : IForecast> T.isTomorrowForecast: Boolean
        get() = this.differenceInDays == 1

    private val <T : IForecast> T.isBadWeather
        get() = precipitationType != CLEAR &&
                precipitationType != CLOUDS &&
                precipitationType != UNKNOWN

    private val <T : IForecast> T.differenceInDays
        get(): Int {
            val itemCalendar = rightNow.also {
                it.time = Date(dateInSeconds * 1000)
            }

            val nowDay = rightNow.get(Calendar.DAY_OF_MONTH)

            val itemDay = itemCalendar.get(Calendar.DAY_OF_MONTH)

            return itemDay - nowDay
        }

    private val rightNow: Calendar
        get() = Calendar.getInstance()

    private val Context.badTomorrowWeatherNotificationWasShownKey: String
        get() = getString(R.string.bad_tomorrow_weather_notification_was_shown)

    private val Context.sharedPreferences: SharedPreferences
        get() = getDefaultSharedPreferences(this)!!

    private val currentHour: Int
        get() = rightNow.get(Calendar.HOUR_OF_DAY)

    private fun <T : ICurrentWeather> Context.checkCurrentWeather(currentWeather: T) {
        val intent = Intent(this, TabbedWeatherActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val pendingIntent = PendingIntent.getActivity(
            this,
            CURRENT_WEATHER_NOTIFICATION_REQUEST_CODE,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        val temperature = "${currentWeather.temperature}$CELSIUS_DEGREE"

        val weatherDescription =
            "${getString(R.string.weather_description)}: ${currentWeather.weatherDescription}"

        val cloudiness = "${getString(R.string.cloudiness)}: ${currentWeather.cloudiness}%"

        val windSpeed = "${currentWeather.windSpeed} ${getString(R.string.meters_per_second)}"

        val wind = getString(R.string.wind)

        val windDirection = currentWeather.windDirection

        val windInfo = "$wind: $windSpeed, $windDirection"

        val notificationLayout =
            RemoteViews(packageName, R.layout.notification_current_weather).apply {
                setTextViewText(R.id.temperature_CW_N_TV, temperature)
                setTextViewText(R.id.cloudiness_CW_N_TV, cloudiness)
                setTextViewText(R.id.description_CW_N_TV, weatherDescription)
                setTextViewText(R.id.wind_info_CW_N_TV, windInfo)
                setOnClickPendingIntent(R.id.root_CW_N, pendingIntent)
            }

        val notification = NotificationCompat.Builder(this, CURRENT_WEATHER_CHANNEL_ID)
                //TODO make right icon
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContent(notificationLayout)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .setOngoing(true)
            .setShowWhen(false)
            .setTicker(temperature)
            .build()

        with(NotificationManagerCompat.from(this)) {
            notify(CURRENT_WEATHER_NOTIFICATION_ID, notification)
        }
    }

    /*@SuppressLint("SetTextI18n")
    private fun Context.doIcon(temperature: String) {
        val textView = TextView(this)
        textView.text = "Hello World"

        textView.isDrawingCacheEnabled = true
        textView.destroyDrawingCache()
        textView.buildDrawingCache()

//        val bitmap = getTransparentBitmapCopy(textView.drawingCache)
    }

    private fun getTransparentBitmapCopy(source: Bitmap): Bitmap {
        val width = source.width
        val height = source.height
        val copy = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val pixels = IntArray(width * height)

        source.getPixels(pixels, 0, width, 0, 0, width, height)
        copy.setPixels(pixels, 0, width, 0, 0, width, height)
        return copy
    }

    private fun shortcutAdd(name: String, number: Int) {
        // Intent to be send, when shortcut is pressed by user ("launched")
//        val shortcutIntent = Intent(getApplicationContext(), Play::class.java)
//        shortcutIntent.action = Constants.ACTION_PLAY

        // Create bitmap with number in it -> very default. You probably want to give it a more stylish look
        val bitmap = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888)
        val paint = Paint()
        paint.color = -0x7f7f80 // gray
        paint.textAlign = Paint.Align.CENTER
        paint.textSize = 50f
        Canvas(bitmap).drawText("" + number, 50f, 50f, paint)
//        (findViewById(R.id.icon) as ImageView).setImageBitmap(bitmap)

        // Decorate the shortcut
//        val addIntent = Intent()
//        addIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortcutIntent)
//        addIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, name)
//        addIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON, bitmap)

        // Inform launcher to create shortcut
//        addIntent.action = "com.android.launcher.action.INSTALL_SHORTCUT"
//        getApplicationContext().sendBroadcast(addIntent)
    }*/

}
