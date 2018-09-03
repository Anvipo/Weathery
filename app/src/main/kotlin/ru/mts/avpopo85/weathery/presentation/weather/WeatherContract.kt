package ru.mts.avpopo85.weathery.presentation.weather

import ru.mts.avpopo85.weathery.models.weather.yandex.WeatherResponse
import ru.mts.avpopo85.weathery.presentation.base.BaseContract

interface WeatherContract : BaseContract {

    interface WeatherView : BaseContract.View {
        fun showLoadingProgress()
        fun hideLoadingProgress()
        fun showWeatherResponse(weatherResponse: WeatherResponse)
        fun showError(throwable: Throwable)
    }

    interface WeatherPresenter : BaseContract.Presenter<WeatherView> {
        fun onButtonClick()
    }
}