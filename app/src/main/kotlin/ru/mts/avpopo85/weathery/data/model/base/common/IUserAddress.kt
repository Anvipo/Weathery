package ru.mts.avpopo85.weathery.data.model.base.common

import ru.mts.avpopo85.weathery.data.model.implementation.common.GeographicCoordinates
import ru.mts.avpopo85.weathery.data.model.implementation.common.UserLocale

interface IUserAddress {

    val saveDate: Long

    /**E.g. region of country*/
    val adminArea: String?

    val countryCode: String?

    val countryName: String?

    /**E.g. building*/
    val featureName: String?

    val coords: GeographicCoordinates?

    val locale: UserLocale?

    /**E.g. district of city*/
    val locality: String?

    val postalCode: Int?

    /**E.g. city*/
    val subAdminArea: String?

    /**E.g. building*/
    val subThoroughfare: String?

    /**E.g. street*/
    val thoroughfare: String?

    val extras: String?

    val phone: String?

    val premises: String?

    val subLocality: String?

    val url: String?

}