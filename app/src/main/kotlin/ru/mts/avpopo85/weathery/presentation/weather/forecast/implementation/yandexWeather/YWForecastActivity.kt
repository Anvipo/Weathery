package ru.mts.avpopo85.weathery.presentation.weather.forecast.implementation.yandexWeather

import androidx.appcompat.widget.Toolbar
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import kotlinx.android.synthetic.main.activity_yw_forecast.*
import kotlinx.android.synthetic.main.appbar.*
import kotlinx.android.synthetic.main.content_yw_forecast.*
import ru.mts.avpopo85.weathery.R
import ru.mts.avpopo85.weathery.application.App
import ru.mts.avpopo85.weathery.di.modules.yandexWeather.YWForecastModule
import ru.mts.avpopo85.weathery.presentation.weather.forecast.base.view.activity.AbsForecastActivity
import ru.mts.avpopo85.weathery.presentation.weather.forecast.base.ForecastContract
import ru.mts.avpopo85.weathery.presentation.weather.forecast.implementation.openWeatherMap.adapter.base.IForecastAdapter
import ru.mts.avpopo85.weathery.utils.yandexWeather.YWForecastType
import javax.inject.Inject

class YWForecastActivity : AbsForecastActivity<YWForecastType>() {

    @Inject
    override lateinit var presenter: ForecastContract.Presenter<YWForecastType>

    override val swipeRefreshLayout: SwipeRefreshLayout by lazy { yw_forecast_SRL }

    override val rootLayout: CoordinatorLayout by lazy { yw_forecast_CL }

    override val clickListener: (YWForecastType) -> Unit by lazy {
        TODO("not implemented")
    }

    override val adapter: IForecastAdapter<YWForecastType> by lazy {
        TODO("not implemented")
    }

    override val layoutResID: Int by lazy { R.layout.activity_yw_forecast }

    override val viewToolbar: Toolbar by lazy { toolbar }

    override val recyclerViewId: Int by lazy {
        TODO("not implemented")
    }

    override fun initInjection() {
        App.appComponent
            .plus(YWForecastModule(this))
            .inject(this)
    }

    override fun showWeatherInfo(itemData: YWForecastType) {
        TODO("not implemented")
    }

    override fun onNewLocation() {
        TODO("not implemented")
    }

}
