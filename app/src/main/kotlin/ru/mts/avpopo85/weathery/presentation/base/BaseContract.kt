package ru.mts.avpopo85.weathery.presentation.base

import ru.mts.avpopo85.weathery.presentation.base.utils.SnackbarLengths

interface BaseContract {

    interface View {

        fun showError(error: Throwable, isCritical: Boolean = false)

        fun showError(message: String, isCritical: Boolean = false)

        fun showSnackbar(
            message: String,
            length: SnackbarLengths = SnackbarLengths.LENGTH_LONG,
            rootView: android.view.View? = null
        )

        fun showAlertDialog(
            message: String,
            positiveButtonText: String,
            negativeButtonText: String,
            onClickedPositiveButton: () -> Unit = {},
            onClickedNegativeButton: () -> Unit = {},
            title: String? = null
        )

        fun changeTitle(title: String) = Unit

    }

    interface Presenter<in V : View> {

        fun onBindView(view: V)

        fun onUnbindView()

    }

}
