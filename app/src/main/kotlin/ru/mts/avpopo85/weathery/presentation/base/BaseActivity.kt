package ru.mts.avpopo85.weathery.presentation.base

import android.support.v7.app.AppCompatActivity
import org.jetbrains.anko.longToast

abstract class BaseActivity : AppCompatActivity(), BaseContract.View {

    override fun showError(throwable: Throwable) {
        longToast(throwable.message ?: "")
    }

    override fun showError(message: String?) {
        if (message != null) longToast(message)
    }

}
