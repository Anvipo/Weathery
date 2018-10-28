package ru.mts.avpopo85.weathery.presentation.weather.forecast.implementation.openWeatherMap.view.activity

import androidx.appcompat.widget.Toolbar
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import kotlinx.android.synthetic.main.appbar.*
import kotlinx.android.synthetic.main.content_owm_forecast.*
import kotlinx.android.synthetic.main.view_owm_forecast.*
import ru.mts.avpopo85.weathery.R
import ru.mts.avpopo85.weathery.application.App
import ru.mts.avpopo85.weathery.di.modules.openWeatherMap.OWMForecastModule
import ru.mts.avpopo85.weathery.presentation.weather.forecast.base.ForecastContract
import ru.mts.avpopo85.weathery.presentation.weather.forecast.base.view.activity.AbsForecastActivity
import ru.mts.avpopo85.weathery.presentation.weather.forecast.implementation.openWeatherMap.adapter.base.IForecastAdapter
import ru.mts.avpopo85.weathery.presentation.weather.forecast.implementation.openWeatherMap.adapter.implementation.OWMForecastAdapter
import ru.mts.avpopo85.weathery.presentation.weather.forecast.implementation.openWeatherMap.utils.FORECAST_INFO_INTENT_TAG
import ru.mts.avpopo85.weathery.utils.openWeatherMap.OWMForecastType
import javax.inject.Inject

//TODO: delete
class OWMForecastActivity : AbsForecastActivity<OWMForecastType>() {

    @Inject
    override lateinit var presenter: ForecastContract.Presenter<OWMForecastType>

    override val swipeRefreshLayout: SwipeRefreshLayout by lazy { owm_forecast_SRL }

    override val rootLayout: CoordinatorLayout by lazy { owm_forecast_CL }

    override val clickListener: (OWMForecastType) -> Unit = { presenter.onItemClicked(it) }

    override val layoutResID: Int by lazy { R.layout.view_owm_forecast }

    override val viewToolbar: Toolbar by lazy { toolbar }

    override val recyclerViewId: Int by lazy { R.id.owm_forecast_RV }

    override val adapter: IForecastAdapter<OWMForecastType>
            by lazy { OWMForecastAdapter(clickListener) }

    override fun initInjection() {
        App.appComponent
            .plus(OWMForecastModule(this))
            .inject(this)
    }

    override fun showWeatherInfo(itemData: OWMForecastType) {
        startActivity<OWMForecastInfoActivity>(FORECAST_INFO_INTENT_TAG to itemData)
    }

    override fun onNewLocation() {
        TODO("not implemented")
    }

}
