package ru.mts.avpopo85.weathery.presentation.base

interface BaseContract {

    interface View {

        fun showError(error: Throwable)

        fun showError(message: String)

        fun showShortSnackbar(message: String, view: android.view.View? = null)

        fun showLongSnackbar(message: String, view: android.view.View? = null)

        fun showIndefiniteSnackbar(message: String, view: android.view.View? = null)

        fun showAlertDialog(
            message: String,
            positiveButtonText: String,
            negativeButtonText: String,
            onClickedPositiveButton: () -> Unit = {},
            onClickedNegativeButton: () -> Unit = {},
            title: String? = null
        )

        fun changeTitle(title: String) = Unit

        fun getErrorMessageOrDefault(error: Throwable): String

    }

    interface Presenter<in V : View> {

        fun onBindView(view: V)

        fun onUnbindView()

    }

}
