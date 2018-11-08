package ru.mts.avpopo85.weathery.presentation.settings.implementation.fragments.locationPreference

import io.reactivex.disposables.Disposable
import ru.mts.avpopo85.weathery.di.global.SchedulerManagerModule
import ru.mts.avpopo85.weathery.domain.interactor.base.ILocationPreferenceInteractor
import ru.mts.avpopo85.weathery.presentation.base.common.AbsBasePresenter
import javax.inject.Inject

class LocationPreferencePresenter
@Inject constructor(
    private val interactor: ILocationPreferenceInteractor,
    private val schedulerManagerModule: SchedulerManagerModule
) : AbsBasePresenter<LocationPreferenceContract.View>(), LocationPreferenceContract.Presenter {

    override fun checkLocationInfo() {
        val task: Disposable = interactor.checkLocationInfo()
            .compose(schedulerManagerModule.singleTransformer())
            .subscribe(
                {
                    view?.onSuccessCheckLocationInfo(locationInfo = it)
                },
                {
                    view?.onErrorCheckLocationInfo(error = it)
                }
            )

        compositeDisposable.add(task)
    }

}