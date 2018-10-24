package ru.mts.avpopo85.weathery.data.model.implementation.common

import android.os.Parcelable
import io.realm.RealmObject
import kotlinx.android.parcel.Parcelize
import ru.mts.avpopo85.weathery.data.model.base.openWeatherMap.common.ICoordinates

@Parcelize
open class GeographicCoordinates(

    override var latitude: Double = 0.0,

    override var longitude: Double = 0.0

) : RealmObject(), ICoordinates, Parcelable
