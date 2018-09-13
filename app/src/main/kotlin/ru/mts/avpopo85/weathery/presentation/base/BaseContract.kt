package ru.mts.avpopo85.weathery.presentation.base

import android.content.Context

interface BaseContract {
    interface View {
        val context: Context
    }

    interface Presenter<in T> {
        fun onBindView(view: T)

        fun onStart()

        fun onUnbindView()
    }
}