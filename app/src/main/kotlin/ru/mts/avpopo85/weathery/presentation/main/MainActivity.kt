package ru.mts.avpopo85.weathery.presentation.main

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.startActivity
import ru.mts.avpopo85.weathery.R
import ru.mts.avpopo85.weathery.presentation.weather.weather.OWMActivity
import ru.mts.avpopo85.weathery.presentation.weather.weather.YWActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        yw_B.setOnClickListener {
            startActivity<YWActivity>()
        }

        owm_B.setOnClickListener {
            startActivity<OWMActivity>()
        }
    }
}
