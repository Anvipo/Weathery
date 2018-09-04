package ru.mts.avpopo85.weathery.presentation.base

interface BaseContract {
    interface View

    interface Presenter<in T> {
        fun onBindView(view: T)

        fun onStart()

        fun onUnbindView()
    }
}