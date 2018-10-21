package ru.mts.avpopo85.weathery.data.model.implementation.common

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import ru.mts.avpopo85.weathery.data.model.base.common.IUserAddress

open class UserAddress(

    override var saveDate: Long = 0,

    override var adminArea: String? = null,

    override var countryCode: String? = null,

    override var countryName: String? = null,

    override var featureName: String? = null,

    override var coords: GeographicCoordinates? = null,

    override var locale: UserLocale? = null,

    @PrimaryKey
    override var locality: String? = null,

    override var postalCode: String? = null,

    override var subAdminArea: String? = null,

    override var subThoroughfare: String? = null,

    override var thoroughfare: String? = null,

    override var extras: String? = null,

    override var phone: String? = null,

    override var premises: String? = null,

    override var subLocality: String? = null,

    override var url: String? = null

) : RealmObject(), IUserAddress
