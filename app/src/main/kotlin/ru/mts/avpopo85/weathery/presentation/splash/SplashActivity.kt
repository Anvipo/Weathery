package ru.mts.avpopo85.weathery.presentation.splash

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_splash.*
import org.jetbrains.anko.startActivity
import ru.mts.avpopo85.weathery.R
import ru.mts.avpopo85.weathery.presentation.main.MainActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        goBtn.setOnClickListener {
            startActivity<MainActivity>()
        }
    }
}
