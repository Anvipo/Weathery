package ru.mts.avpopo85.weathery.data.model.base.common

import ru.mts.avpopo85.weathery.data.model.implementation.common.GeographicCoordinates
import ru.mts.avpopo85.weathery.data.model.implementation.common.UserLocale

interface IUserAddress {

    val saveDate: Long

    /**Region*/
    val adminArea: String?

    val countryCode: String?

    /**Building*/
    val countryName: String?

    val featureName: String?

    val coords: GeographicCoordinates?

    val locale: UserLocale?

    val locality: String?

    val postalCode: Int?

    /**City*/
    val subAdminArea: String?

    /**Building*/
    val subThoroughfare: String?

    /**Street*/
    val thoroughfare: String?

    val extras: String?

    val phone: String?

    val premises: String?

    val subLocality: String?

    val url: String?

}