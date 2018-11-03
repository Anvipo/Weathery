package ru.mts.avpopo85.weathery.presentation.settings.implementation

import io.reactivex.disposables.Disposable
import ru.mts.avpopo85.weathery.di.global.SchedulerManagerModule
import ru.mts.avpopo85.weathery.domain.interactor.base.ISettingsInteractor
import ru.mts.avpopo85.weathery.presentation.base.common.AbsBasePresenter
import ru.mts.avpopo85.weathery.presentation.settings.base.SettingsContract
import javax.inject.Inject

class SettingsPresenter
@Inject constructor(
    private val interactor: ISettingsInteractor,
    private val schedulerManagerModule: SchedulerManagerModule
) : AbsBasePresenter<SettingsContract.View>(), SettingsContract.Presenter {

    override fun checkPreferences() {
        val task: Disposable = interactor.checkPreferences()
            .compose(schedulerManagerModule.singleTransformer())
            .subscribe(
                {
                    view?.onSuccessCheckPreferences(it)
                },
                {
                    view?.showError(error = it)
                }
            )

        compositeDisposable.add(task)
    }

}