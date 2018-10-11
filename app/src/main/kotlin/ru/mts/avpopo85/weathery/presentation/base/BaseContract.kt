package ru.mts.avpopo85.weathery.presentation.base

interface BaseContract {

    interface View {

        fun showError(throwable: Throwable)

        fun showError(message: String)

        fun showToast(message: String)

        fun showLongToast(message: String)

        fun showIndefiniteSnackbar(message: String?, view: android.view.View?)

        fun showAlertDialog(
            message: String,
            positiveButtonText: String,
            negativeButtonText: String,
            onClickedPositiveButton: () -> Unit = {},
            onClickedNegativeButton: () -> Unit = {},
            title: String? = null
        )

    }

    interface Presenter<in V : View> {

        fun onBindView(view: V)

        fun onUnbindView()

    }

}
