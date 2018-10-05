package ru.mts.avpopo85.weathery.presentation.base

import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import com.google.android.material.snackbar.Snackbar
import org.jetbrains.anko.longToast
import org.jetbrains.anko.toast

//TODO как это реализовать, чтобы прокинуть это всё к наследникам?
abstract class AbsBaseActivity/*<out P : BaseContract.Presenter<BaseContract.View>>*/ :
    AppCompatActivity(),
    BaseContract.View {

    /*protected abstract val presenter: P

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        presenter.onBindView(this)
    }

    override fun onDestroy() {
        presenter.onUnbindView()
        super.onDestroy()
    }*/

    protected abstract val progressBar: ProgressBar

    override fun showLoadingProgress() {
        progressBar.visibility = View.VISIBLE
    }

    override fun hideLoadingProgress() {
        progressBar.visibility = View.GONE
    }

    override fun showError(throwable: Throwable) {
        longToast(throwable.message ?: "")
    }

    override fun showError(message: String) {
        longToast(message)
    }

    override fun showToast(message: String) {
        toast(message)
    }

    override fun showLongToast(message: String) {
        longToast(message)
    }

    override fun showIndefiniteSnackbar(message: String, view: View) {
        Snackbar
            .make(view, message, Snackbar.LENGTH_INDEFINITE)
            .apply { show() }
    }
}
