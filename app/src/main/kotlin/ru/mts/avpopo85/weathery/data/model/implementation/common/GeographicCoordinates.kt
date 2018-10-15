package ru.mts.avpopo85.weathery.data.model.implementation.common

import io.realm.RealmObject
import ru.mts.avpopo85.weathery.data.model.base.openWeatherMap.common.ICoordinates

open class GeographicCoordinates(

    override var latitude: Double? = null,

    override var longitude: Double? = null

) : RealmObject(), ICoordinates {

    fun latitudeAndLongitudeAreNotNull(): Boolean = latitude != null && longitude != null

}