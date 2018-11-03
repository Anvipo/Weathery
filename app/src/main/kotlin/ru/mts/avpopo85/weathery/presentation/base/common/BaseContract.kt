package ru.mts.avpopo85.weathery.presentation.base.common

interface BaseContract {

    interface View {

        fun onUnexpectedApplicationBehavior(rootView: android.view.View? = null)

        fun showError(
            error: Throwable,
            isCritical: Boolean = false,
            rootView: android.view.View? = null
        )

        fun showError(
            message: String,
            isCritical: Boolean = false,
            rootView: android.view.View? = null
        )

        fun showShortSnackbar(message: String, rootView: android.view.View? = null)

        fun showLongSnackbar(message: String, rootView: android.view.View? = null)

        fun showIndefiniteSnackbar(message: String, rootView: android.view.View? = null)

        fun changeTitle(title: String)

        fun notifyAbout(error: Throwable)

    }

    interface Presenter<in V : View> {

        fun onBindView(view: V)

        fun onUnbindView()

        fun clearCompositeDisposable()

    }

}
