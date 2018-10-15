package ru.mts.avpopo85.weathery.data.model.implementation.common

import io.realm.RealmObject
import ru.mts.avpopo85.weathery.data.model.base.openWeatherMap.common.ICoordinates

open class GeographicCoordinates(

    override var latitude: Double = 0.0,

    override var longitude: Double = 0.0

) : RealmObject(), ICoordinates
