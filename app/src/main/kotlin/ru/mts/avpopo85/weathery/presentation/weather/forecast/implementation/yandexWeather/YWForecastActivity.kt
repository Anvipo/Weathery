package ru.mts.avpopo85.weathery.presentation.weather.forecast.implementation.yandexWeather

import android.os.Bundle
import android.view.View.GONE
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import kotlinx.android.synthetic.main.activity_yw_forecast.*
import kotlinx.android.synthetic.main.appbar.*
import kotlinx.android.synthetic.main.content_yw_forecast.*
import ru.mts.avpopo85.weathery.R
import ru.mts.avpopo85.weathery.application.App
import ru.mts.avpopo85.weathery.di.modules.yandexWeather.YWForecastModule
import ru.mts.avpopo85.weathery.presentation.utils.ARG_FORECAST
import ru.mts.avpopo85.weathery.presentation.weather.forecast.base.AbsForecastActivity
import ru.mts.avpopo85.weathery.presentation.weather.forecast.base.ForecastContract
import ru.mts.avpopo85.weathery.presentation.weather.forecast.implementation.openWeatherMap.adapter.base.IForecastAdapter
import ru.mts.avpopo85.weathery.presentation.weather.forecast.implementation.yandexWeather.adapters.YWForecastActivityPagerAdapter
import ru.mts.avpopo85.weathery.utils.yandexWeather.YWForecastListType
import ru.mts.avpopo85.weathery.utils.yandexWeather.YWForecastType
import javax.inject.Inject

class YWForecastActivity : AbsForecastActivity<YWForecastType>() {

    @Inject
    lateinit var presenter: ForecastContract.Presenter<YWForecastType>

    override val swipeRefreshLayout: SwipeRefreshLayout by lazy { yw_forecast_SRL }

    override val rootLayout: CoordinatorLayout by lazy { yw_forecast_CL }

    override val clickListener: (YWForecastType) -> Unit by lazy {
        TODO("not implemented")
    }

    override val mAdapter: IForecastAdapter<YWForecastType> by lazy {
        TODO("NOT IMPLEMENTED")
    }

    private lateinit var pagerAdapter: YWForecastActivityPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initView()

        initInjection()

        initBindings()

        initRecyclerView()
    }

    override fun onDestroy() {
        unbindPresenter()
        super.onDestroy()
    }

    override fun initBindings() {
        setOnRefreshListener()

        bindPresenter()
        presenter.loadWeatherData()
    }

    override fun bindPresenter() {
        presenter.onBindView(this)
    }

    override fun unbindPresenter() {
        presenter.onUnbindView()
    }

    override fun initInjection() {
        App.appComponent
            .plus(YWForecastModule(this))
            .inject(this)
    }

    override fun initView() {
        setContentView(R.layout.activity_yw_forecast)

        toolbar.title = getString(R.string.forecast)

        setSupportActionBar(toolbar)
    }

    override fun setOnRefreshListener() {
        swipeRefreshLayout.setOnRefreshListener(this)
    }

    override fun onRefresh() {
        presenter.loadWeatherData()
    }

    override fun showWeatherResponse(data: YWForecastListType) {
        if (data.isNotEmpty()) {
            showLayout()
            fillRecyclerView(data)
        } else
            hideLayout()
    }

    override fun fillRecyclerView(data: YWForecastListType) {
        for (forecast in data) {
            val yfr = YWForecastFragment()

            yfr.arguments = Bundle().apply {
                putParcelable(ARG_FORECAST, forecast)
            }

            pagerAdapter.addFragment(yfr, forecast.date)
        }
    }

    override fun changeTitle(title: String) {
        toolbar.title = title
    }

    override fun initRecyclerView() {
        pagerAdapter = YWForecastActivityPagerAdapter(supportFragmentManager)
        yw_forecast_VP.adapter = pagerAdapter
        yw_forecast_VP.visibility = GONE
    }

    override fun startWeatherInfoActivity(itemData: YWForecastType) {
        TODO("not implemented")
    }

}
