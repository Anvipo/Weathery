package ru.mts.avpopo85.weathery.presentation.weather.forecast.implementation.openWeatherMap

import android.os.Bundle
import android.widget.ProgressBar
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_owm_forecast.*
import kotlinx.android.synthetic.main.activity_yw_forecast.*
import org.jetbrains.anko.longToast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.mts.avpopo85.weathery.R
import ru.mts.avpopo85.weathery.application.App
import ru.mts.avpopo85.weathery.data.network.retrofit.openWeatherMap.IOWMForecastApiService
import ru.mts.avpopo85.weathery.data.utils.openWeatherMap.OWMConstants.BASE_URL
import ru.mts.avpopo85.weathery.data.utils.openWeatherMap.OWMConstants.KRASNODAR_ID
import ru.mts.avpopo85.weathery.di.modules.OpenWeatherMapModule
import ru.mts.avpopo85.weathery.presentation.weather.forecast.base.AbsForecastActivity
import ru.mts.avpopo85.weathery.presentation.weather.forecast.base.ForecastContract
import ru.mts.avpopo85.weathery.utils.openWeatherMap.OWMForecastResponseType
import ru.mts.avpopo85.weathery.utils.yandexWeather.YWForecastListType

class OWMForecastActivity : AbsForecastActivity(),
    ForecastContract.View<YWForecastListType> {

//    @Inject
//    lateinit var forecastPresenter: ForecastContract.Presenter<YWForecastListType>

    override val progressBar: ProgressBar by lazy { yandex_forecast_PB }

//    private lateinit var pagerAdapter: ForecastActivityPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_owm_forecast)

        App.appComponentForYandexWeather.plus(OpenWeatherMapModule())
            .inject(this)

        val gson = GsonBuilder()
            .setLenient()
            .setPrettyPrinting()
            .create()

        val retrofit = Retrofit
            .Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl(BASE_URL)
            .build()

        val apiService = retrofit.create(IOWMForecastApiService::class.java)

        val apiCall = apiService.getForecastById(KRASNODAR_ID)

        apiCall.enqueue(object : Callback<OWMForecastResponseType> {
            override fun onFailure(call: Call<OWMForecastResponseType>, t: Throwable) {
                val c = 1
                longToast(t.message ?: "Ошибка в ${this::class.java.simpleName}")
            }

            override fun onResponse(
                call: Call<OWMForecastResponseType>,
                response: Response<OWMForecastResponseType>
            ) {
                if (response.isSuccessful) {
                    val body = response.body()

                    if (body != null) {
                        val city = body.city
                        city.coordinates
                        owm_TV.text = ""
                    } else {
                        val c = 1
                    }
                } else {
                    val c = 1
                }
            }

        })


//        initPager()

//        forecastPresenter.onBindView(this)
//        forecastPresenter.loadForecast()
    }

    /*override fun onDestroy() {
        forecastPresenter.onUnbindView()
        super.onDestroy()
    }*/

    override fun showWeatherResponse(data: YWForecastListType) {
//        if (data.isNotEmpty()) {
//            pager.visibility = VISIBLE
//            putForecastDataInPager(data)
//        } else
//            pager.visibility = GONE
    }

    /*private fun putForecastDataInPager(data: YWForecastListType) {
        for (forecast in data) {
            val yfr = YWForecastFragment()

            yfr.arguments = Bundle().apply {
                putParcelable(ARG_FORECAST, forecast)
            }

            val title = makeTitle(forecast)

            pagerAdapter.addFragment(yfr, title)
        }
    }*/

    /*private fun initPager() {
        pagerAdapter = ForecastActivityPagerAdapter(supportFragmentManager)
        pager.adapter = pagerAdapter
        pager.visibility = GONE
    }*/

}
