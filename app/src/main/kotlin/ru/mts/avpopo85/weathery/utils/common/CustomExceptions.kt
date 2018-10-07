package ru.mts.avpopo85.weathery.utils.common

sealed class MyRealmException(cause: String) : Throwable() {
    override val message: String = cause

    class DBHasNothingAndGPSOffException(cause: String) : MyRealmException(cause)
    class DBHasNothingAndGPSOnException(cause: String) : MyRealmException(cause)
    class DBHasNoFieldAndGetGeolocationException(cause: String) : MyRealmException(cause)
    class DBHasNothingAndGetGeolocationException(cause: String) : MyRealmException(cause)
}