package ru.mts.avpopo85.weathery.presentation.base

interface BaseContract {

    interface View {

        fun showError(throwable: Throwable)

        fun showError(message: String?)

        fun hideLayout()

        fun showLayout()

    }

    interface Presenter<in V> {

        fun onBindView(view: V)

        fun onUnbindView()

    }

}
