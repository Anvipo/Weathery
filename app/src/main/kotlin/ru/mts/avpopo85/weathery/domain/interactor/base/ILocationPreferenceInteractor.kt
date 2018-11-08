package ru.mts.avpopo85.weathery.domain.interactor.base

import io.reactivex.Single
import ru.mts.avpopo85.weathery.domain.model.implementation.settings.LocationInfo

interface ILocationPreferenceInteractor {

    fun checkLocationInfo(): Single<LocationInfo>

}