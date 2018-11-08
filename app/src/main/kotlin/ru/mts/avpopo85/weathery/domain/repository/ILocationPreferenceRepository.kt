package ru.mts.avpopo85.weathery.domain.repository

import io.reactivex.Single
import ru.mts.avpopo85.weathery.domain.model.implementation.settings.LocationInfo

interface ILocationPreferenceRepository {

    fun checkPreferences(): Single<LocationInfo>

}
