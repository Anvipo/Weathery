package ru.mts.avpopo85.weathery.presentation.weather.yandexWeather.forecast

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import kotlinx.android.synthetic.main.activity_yandex_forecast.*
import org.jetbrains.anko.longToast
import ru.mts.avpopo85.weathery.application.App
import ru.mts.avpopo85.weathery.R
import ru.mts.avpopo85.weathery.di.weather.yandexWeather.YandexWeatherModule
import ru.mts.avpopo85.weathery.presentation.weather.WeatherContract
import javax.inject.Inject

class YandexForecastActivity : AppCompatActivity(), WeatherContract.WeatherView {
    @Inject
    lateinit var yandexForecastPresenter: YandexForecastPresenter

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_yandex_forecast)

        App.appComponentForYandexWeather.plus(YandexWeatherModule()).inject(this)
        yandexForecastPresenter.onBindView(this)

        initRecyclerView()

        yandexForecastPresenter.onStart()
    }

    private fun initRecyclerView() {
        viewManager = LinearLayoutManager(this)
        viewAdapter = YandexWeatherAdapter()

        /*recyclerView = yandex_forecast_recycler_view.apply {
            setHasFixedSize(true)

            layoutManager = viewManager

            adapter = viewAdapter
        }*/
    }

    override fun onDestroy() {
        yandexForecastPresenter.onUnbindView()
        super.onDestroy()
    }

    override fun showLoadingProgress() {
        yandex_forecast_PB.visibility = View.VISIBLE
    }

    override fun hideLoadingProgress() {
        yandex_forecast_PB.visibility = View.GONE
    }

    override fun showWeatherResponse(weatherResponse: String) {
        forecastTV.text = weatherResponse
    }

    override fun showError(throwable: Throwable) {
        val str = throwable.message ?: ""
        longToast(str)
    }

    override fun showError(message: String) {
        longToast(message)
    }
}