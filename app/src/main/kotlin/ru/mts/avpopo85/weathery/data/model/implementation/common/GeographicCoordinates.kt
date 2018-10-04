package ru.mts.avpopo85.weathery.data.model.implementation.common

import io.realm.RealmObject

open class GeographicCoordinates(

    var latitude: Double? = null,

    var longitude: Double? = null

) : RealmObject() {

    fun latitudeAndLongitudeAreNotNull(): Boolean = latitude != null && longitude != null

}