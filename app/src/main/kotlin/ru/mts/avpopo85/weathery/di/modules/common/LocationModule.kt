package ru.mts.avpopo85.weathery.di.modules.common

import com.tbruyelle.rxpermissions2.RxPermissions
import dagger.Module
import dagger.Provides
import ru.mts.avpopo85.weathery.data.repository.common.PermissionsRepository
import ru.mts.avpopo85.weathery.di.global.SchedulerManagerModule
import ru.mts.avpopo85.weathery.di.scopes.common.LocationScope
import ru.mts.avpopo85.weathery.domain.interactor.base.ILocationInteractor
import ru.mts.avpopo85.weathery.domain.interactor.implementation.common.LocationInteractor
import ru.mts.avpopo85.weathery.domain.repository.ILocationRepository
import ru.mts.avpopo85.weathery.domain.repository.IPermissionsRepository
import ru.mts.avpopo85.weathery.presentation.location.implementation.LocationActivity
import ru.mts.avpopo85.weathery.presentation.location.implementation.LocationPresenter
import ru.mts.avpopo85.weathery.presentation.location.base.LocationContract

@Module
class LocationModule(private val context: LocationActivity) {

    @Suppress("Annotator")
    @Provides
    @LocationScope
    fun providePermissionsRepository(): IPermissionsRepository =
        PermissionsRepository(RxPermissions(context))

    @Provides
    @LocationScope
    fun provideInteractor(
        permissionsRepository: IPermissionsRepository,
        locationRepository: ILocationRepository
    ): ILocationInteractor = LocationInteractor(permissionsRepository, locationRepository)

    @Provides
    @LocationScope
    fun providePresenter(
        interactor: ILocationInteractor,
        schedulerManagerModule: SchedulerManagerModule
    ): LocationContract.Presenter =
        LocationPresenter(
            interactor,
            schedulerManagerModule
        )

}
