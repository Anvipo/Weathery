package ru.mts.avpopo85.weathery.presentation.main

import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.appbar.*
import kotlinx.android.synthetic.main.content_main.*
import ru.mts.avpopo85.weathery.R
import ru.mts.avpopo85.weathery.presentation.base.activity.AbsBaseActivity
import ru.mts.avpopo85.weathery.presentation.weather.main.YWActivity
import ru.mts.avpopo85.weathery.presentation.weather.tab.TabbedWeather

class MainActivity : AbsBaseActivity() {

    override val rootLayout: View by lazy { main_CL }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar.title = getString(R.string.api_selection)

        setSupportActionBar(toolbar)

        yw_B.setOnClickListener {
            startActivity<YWActivity>()
        }

        owm_B.setOnClickListener {
            startActivity<TabbedWeather>()
        }
    }
}
