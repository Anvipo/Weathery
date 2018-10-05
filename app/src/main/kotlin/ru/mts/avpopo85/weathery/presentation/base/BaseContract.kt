package ru.mts.avpopo85.weathery.presentation.base

interface BaseContract {

    interface View {

        fun showLoadingProgress()

        fun hideLoadingProgress()

        fun showError(throwable: Throwable)

        fun showError(message: String)

        fun showToast(message: String)

        fun showLongToast(message: String)

        fun showIndefiniteSnackbar(message: String, view: android.view.View)

    }

    interface Presenter<in V : View> {

        fun onBindView(view: V)

        fun onUnbindView()

    }

}
