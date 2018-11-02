package ru.mts.avpopo85.weathery.presentation.weather.forecast.base.view.fragment

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.app.NotificationCompat
import androidx.core.app.TaskStackBuilder
import androidx.core.content.getSystemService
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.mts.avpopo85.weathery.R
import ru.mts.avpopo85.weathery.domain.model.base.common.IForecast
import ru.mts.avpopo85.weathery.presentation.utils.BAD_WEATHER_IS_COMING_ID
import ru.mts.avpopo85.weathery.presentation.weather.base.view.fragment.AbsWeatherFragment
import ru.mts.avpopo85.weathery.presentation.weather.forecast.base.ForecastContract
import ru.mts.avpopo85.weathery.presentation.weather.forecast.implementation.openWeatherMap.adapter.base.IForecastAdapter
import ru.mts.avpopo85.weathery.presentation.weather.tab.TabbedWeatherActivity
import ru.mts.avpopo85.weathery.utils.common.PrecipitationType.*
import java.util.*


abstract class AbsForecastFragment<T : IForecast> :
    AbsWeatherFragment(),
    ForecastContract.View<T> {

    abstract val itemClickListener: (T) -> Unit

    final override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()
    }

    @ExperimentalUnsignedTypes
    final override fun showWeatherResponse(forecast: List<T>) {
        if (forecast.isNotEmpty()) {
            updateRecyclerViewData(data = forecast)
            showLayout()
            checkPrecipitation(forecast = forecast)
        } else
            hideLayout()
    }

    final override fun bindPresenter() {
        presenter.onBindView(view = this)
    }

    final override fun unbindPresenter() {
        presenter.onUnbindView()
    }

    final override fun onRefresh() {
        presenter.onSwipeToRefresh()
    }

    final override fun onStart() {
        super.onStart()

        presenter.loadWeatherData()
    }

    final override fun onStop() {
        super.onStop()

        hideLayout()
    }

    final override val toolbarTitle: String by lazy { getString(R.string.forecast) }

    protected open lateinit var presenter: ForecastContract.Presenter<T>

    protected abstract val adapter: IForecastAdapter<T>

    protected abstract val recyclerView: RecyclerView

    //TODO
    private fun checkPrecipitation(forecast: List<T>) {
        val tommorowForecast = getTommorowForecast(forecast)

        //tomorrow
        val badWeather = forecast.filter(::isBadWeather)

        if (badWeather.isNotEmpty()) {
            for (hourForecast in badWeather) {
                if (hourForecast.cloudiness.toDouble() > 50.0) {
                    val c = 1
                    //notificate
                } else {
                    val mBuilder = NotificationCompat.Builder(context!!, "")
                        .setSmallIcon(R.drawable.ic_launcher_foreground)
                        .setContentTitle("Будет хорошая погода")
                        .setContentText("${hourForecast.date} ожидается хорошая погода")
                        .setPriority(NotificationCompat.PRIORITY_MAX)

                    val resultIntent = Intent(context!!, TabbedWeatherActivity::class.java)

                    val stackBuilder = TaskStackBuilder.create(context!!).apply {
                        addNextIntent(resultIntent)
                    }

                    val resultPendingIntent = stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                    )

                    mBuilder.setContentIntent(resultPendingIntent)

                    val mNotificationManager = context!!.getSystemService<NotificationManager>()!!

                    mNotificationManager.notify(BAD_WEATHER_IS_COMING_ID, mBuilder.build())

                    val c = 1
                    break
                }
            }
        }
    }

    private fun isBadWeather(hourInfo: T) =
        hourInfo.precipitationType != CLEAR &&
                hourInfo.precipitationType != CLOUDS &&
                hourInfo.precipitationType != UNKNOWN

    private fun getTommorowForecast(forecast: List<T>): List<T> =
        forecast.filter(::isItTomorrowForecast)

    private fun isItTomorrowForecast(it: T): Boolean {
        val itemCalendar = Calendar.getInstance().apply {
            time = Date(it.dateInSeconds * 1000)
        }

        val now = Calendar.getInstance()

        val nowDay = now.get(Calendar.DAY_OF_MONTH)

        val itemDay = itemCalendar.get(Calendar.DAY_OF_MONTH)

        return itemDay - nowDay == 1
    }

    private fun updateRecyclerViewData(data: List<T>) {
        adapter.updateData(newData = data)
    }

    private fun initRecyclerView() {
        recyclerView.apply {
            setHasFixedSize(true)

            layoutManager = LinearLayoutManager(this@AbsForecastFragment.context!!)

            adapter = this@AbsForecastFragment.adapter as RecyclerView.Adapter<*>
        }
    }

}
