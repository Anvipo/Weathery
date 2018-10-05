package ru.mts.avpopo85.weathery.presentation.weather.forecast.implementation.openWeatherMap.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

class OWMForecastActivityPagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {
    private val items = mutableListOf<Fragment>()
    private val tabTitles = mutableListOf<String>()

    override fun getItem(position: Int): Fragment = items[position]

    override fun getCount(): Int = items.size

    override fun getPageTitle(position: Int): CharSequence? = tabTitles[position]

    fun addFragment(fragment: Fragment, title: String) {
        items.add(fragment)
        tabTitles.add(title)
    }
}