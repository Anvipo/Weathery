package ru.mts.avpopo85.weathery.presentation.weather.yandex

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import kotlinx.android.synthetic.main.activity_yandex_weather.*
import org.jetbrains.anko.longToast
import ru.mts.avpopo85.weathery.App
import ru.mts.avpopo85.weathery.R
import ru.mts.avpopo85.weathery.di.weather.YandexWeatherModule
import ru.mts.avpopo85.weathery.models.weather.yandex.WeatherResponse
import ru.mts.avpopo85.weathery.presentation.weather.WeatherContract
import javax.inject.Inject

class YandexWeatherActivity : AppCompatActivity(), WeatherContract.WeatherView {
    @Inject
    lateinit var presenter: YandexWeatherPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_yandex_weather)

        App.appComponentForYandexWeather.plus(YandexWeatherModule()).inject(this)
//        App.appComponentForYandexWeather().plus(YandexWeatherModule()).inject(this)
//        App.instance.appComponentForYandexWeather().plus(YandexWeatherModule()).inject(this)
        presenter.onBindView(this)

        btn.setOnClickListener {
            //            presenter.getCurrentWeatherFor("542420", PossibleUnits.METRIC)
            presenter.onButtonClick(/*"542420", PossibleUnits.METRIC*/)
        }
    }

    override fun onDestroy() {
        presenter.onUnbindView()
        super.onDestroy()
    }

    override fun showLoadingProgress() {
        progressBar.visibility = View.VISIBLE
    }

    override fun hideLoadingProgress() {
        progressBar.visibility = View.GONE
    }

    override fun showWeatherResponse(weatherResponse: WeatherResponse) {
        currentWeatherDataTV.text = weatherResponse.toString()
    }

    override fun showError(throwable: Throwable) {
        val str = throwable.message ?: ""
        longToast(str)
    }

}