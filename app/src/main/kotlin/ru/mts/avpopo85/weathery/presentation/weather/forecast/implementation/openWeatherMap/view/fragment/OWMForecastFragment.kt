package ru.mts.avpopo85.weathery.presentation.weather.forecast.implementation.openWeatherMap.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import kotlinx.android.synthetic.main.content_owm_forecast.*
import kotlinx.android.synthetic.main.view_owm_forecast.*
import ru.mts.avpopo85.weathery.R
import ru.mts.avpopo85.weathery.application.App
import ru.mts.avpopo85.weathery.di.modules.openWeatherMap.OWMForecastModule
import ru.mts.avpopo85.weathery.presentation.weather.forecast.base.ForecastContract
import ru.mts.avpopo85.weathery.presentation.weather.forecast.base.view.fragment.AbsForecastFragment
import ru.mts.avpopo85.weathery.presentation.weather.forecast.implementation.openWeatherMap.adapter.base.IForecastAdapter
import ru.mts.avpopo85.weathery.presentation.weather.forecast.implementation.openWeatherMap.adapter.implementation.OWMForecastAdapter
import ru.mts.avpopo85.weathery.presentation.weather.forecast.implementation.openWeatherMap.utils.FORECAST_INFO_INTENT_TAG
import ru.mts.avpopo85.weathery.presentation.weather.tab.TabbedWeatherActivity
import ru.mts.avpopo85.weathery.utils.openWeatherMap.OWMForecastType
import javax.inject.Inject


class OWMForecastFragment : AbsForecastFragment<OWMForecastType>() {

    override val recyclerView: RecyclerView by lazy { view!!.findViewById<RecyclerView>(R.id.owm_forecast_RV)!! }

    @Inject
    override lateinit var presenter: ForecastContract.Presenter<OWMForecastType>

    override val hidingLayout: View by lazy { owm_forecast_RV }

    override val swipeRefreshLayout: SwipeRefreshLayout by lazy { owm_forecast_SRL }

    override val rootLayout: CoordinatorLayout by lazy { owm_forecast_CL }

    override val itemClickListener: (OWMForecastType) -> Unit = { presenter.onItemClicked(it) }

    override val viewToolbar: ActionBar by lazy { (activity as AppCompatActivity).supportActionBar!! }

    override val adapter: IForecastAdapter<OWMForecastType>
            by lazy { OWMForecastAdapter(itemClickListener) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.view_owm_forecast, container, false)

    override fun initInjection() {
        App.appComponent
            .plus(OWMForecastModule(this.context!!))
            .inject(this)
    }

    override fun showWeatherInfo(itemData: OWMForecastType) {
        (activity!! as TabbedWeatherActivity).clickListener(FORECAST_INFO_INTENT_TAG to itemData)
    }

}