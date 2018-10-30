package ru.mts.avpopo85.weathery.presentation.weather.tab.adapter

import android.content.Context
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.preference.PreferenceManager
import ru.mts.avpopo85.weathery.R
import ru.mts.avpopo85.weathery.presentation.weather.currentWeather.implementation.openWeatherMap.view.fragment.OWMCurrentWeatherFragment
import ru.mts.avpopo85.weathery.presentation.weather.forecast.implementation.openWeatherMap.view.fragment.OWMForecastFragment
import ru.mts.avpopo85.weathery.utils.common.onUnexpectedApplicationBehavior

class SectionsPagerAdapter(
    fragmentManager: FragmentManager,
    private val context: Context,
    private val rootLayout: View
) : FragmentStatePagerAdapter(fragmentManager) {

    override fun getItem(position: Int): Fragment {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)!!

        val weatherAPIPrefKey = context.getString(R.string.pref_key_weather_API)

        val chosenWeatherAPI = sharedPreferences.getString(weatherAPIPrefKey, null)

        return if (chosenWeatherAPI == null || chosenWeatherAPI == context.getString(R.string.open_weather_map)) {
            when (position) {
                0 -> OWMCurrentWeatherFragment()
                else -> OWMForecastFragment()
            }
        } else if (chosenWeatherAPI == context.getString(R.string.yandex_weather)) {
            //todo: when yandex weather api will be available
            when (position) {
                0 -> OWMCurrentWeatherFragment()
                else -> OWMForecastFragment()
            }
        } else {
            context.onUnexpectedApplicationBehavior(rootLayout)

            throw Exception(context.getString(R.string.unexpected_application_behavior))
        }
    }

    override fun getCount(): Int = 2

    override fun getPageTitle(position: Int): CharSequence? = when (position) {
        0 -> context.getString(R.string.current_weather)
        else -> context.getString(R.string.forecast)
    }

}