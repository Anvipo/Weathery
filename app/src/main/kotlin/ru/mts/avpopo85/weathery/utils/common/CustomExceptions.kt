package ru.mts.avpopo85.weathery.utils.common

sealed class MyRealmException(cause: String) : Throwable() {
    override val message: String = cause

    class DBHasNothingAndGetGeolocationException(cause: String) : MyRealmException(cause)

    class DBHasOutdatedWeatherDataException(cause: String, val isConnectedToInternet: Boolean) :
        MyRealmException(cause)

    class DBHasNoWeatherResponseException(cause: String, val isConnectedToInternet: Boolean) :
        MyRealmException(cause)

    class InternetConnectionIsRequiredException(cause: String) : MyRealmException(cause)

    class WrongCityException(cause: String) : MyRealmException(cause)
}