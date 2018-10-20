package ru.mts.avpopo85.weathery.presentation.weather.forecast.implementation.openWeatherMap

import android.os.Bundle
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_owm_forecast.*
import kotlinx.android.synthetic.main.appbar.*
import kotlinx.android.synthetic.main.content_owm_forecast.*
import org.jetbrains.anko.startActivity
import ru.mts.avpopo85.weathery.R
import ru.mts.avpopo85.weathery.application.App
import ru.mts.avpopo85.weathery.di.modules.openWeatherMap.OWMForecastModule
import ru.mts.avpopo85.weathery.presentation.weather.forecast.base.AbsForecastActivity
import ru.mts.avpopo85.weathery.presentation.weather.forecast.base.ForecastContract
import ru.mts.avpopo85.weathery.presentation.weather.forecast.implementation.openWeatherMap.adapter.base.IForecastAdapter
import ru.mts.avpopo85.weathery.presentation.weather.forecast.implementation.openWeatherMap.adapter.implementation.OWMForecastAdapter
import ru.mts.avpopo85.weathery.presentation.weather.forecast.implementation.openWeatherMap.utils.FORECAST_INFO_INTENT_TAG
import ru.mts.avpopo85.weathery.utils.openWeatherMap.OWMForecastListType
import ru.mts.avpopo85.weathery.utils.openWeatherMap.OWMForecastType
import javax.inject.Inject

class OWMForecastActivity : AbsForecastActivity<OWMForecastType>() {

    @Inject
    lateinit var presenter: ForecastContract.Presenter<OWMForecastType>

    override val view: RecyclerView by lazy { owm_forecast_recycler_view }

    override val rootLayout: CoordinatorLayout by lazy { owm_forecast_CL }

    override val clickListener: (OWMForecastType) -> Unit = { presenter.onItemClicked(it) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_owm_forecast)

        toolbar.title = getString(R.string.forecast)

        setSupportActionBar(toolbar)

        App.appComponent
            .plus(OWMForecastModule(this))
            .inject(this)

        initRecyclerView()

        presenter.onBindView(this)
        presenter.loadForecast()
    }

    override fun onDestroy() {
        presenter.onUnbindView()
        super.onDestroy()
    }

    override fun showWeatherResponse(data: OWMForecastListType) {
        if (data.isNotEmpty()) {
            showLayout()
            fillRecyclerView(data)
        } else
            hideLayout()
    }

    override fun fillRecyclerView(data: OWMForecastListType) {
        mAdapter.addAll(data)
    }

    override fun changeTitle(title: String) {
        toolbar.title = title
    }

    override fun initRecyclerView() {
        findViewById<RecyclerView>(R.id.owm_forecast_recycler_view)?.apply {
            setHasFixedSize(true)

            layoutManager = LinearLayoutManager(this@OWMForecastActivity)

            adapter = mAdapter as RecyclerView.Adapter<*>
        }
    }

    override fun startWeatherInfoActivity(itemData: OWMForecastType) {
        startActivity<OWMForecastInfoActivity>(FORECAST_INFO_INTENT_TAG to itemData)
    }

    private val mAdapter: IForecastAdapter<OWMForecastType> by lazy {
        OWMForecastAdapter(clickListener)
    }

}
