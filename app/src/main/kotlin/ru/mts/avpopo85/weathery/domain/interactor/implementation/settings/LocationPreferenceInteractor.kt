package ru.mts.avpopo85.weathery.domain.interactor.implementation.settings

import io.reactivex.Single
import ru.mts.avpopo85.weathery.domain.interactor.base.ILocationPreferenceInteractor
import ru.mts.avpopo85.weathery.domain.model.implementation.settings.LocationInfo
import ru.mts.avpopo85.weathery.domain.repository.ILocationPreferenceRepository
import javax.inject.Inject

class LocationPreferenceInteractor
@Inject constructor(
    private val repository: ILocationPreferenceRepository
) : ILocationPreferenceInteractor {

    override fun checkLocationInfo(): Single<LocationInfo> = repository.checkPreferences()

}