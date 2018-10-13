package ru.mts.avpopo85.weathery.utils.common

sealed class MyRealmException(message: String) : Throwable(message) {

    class DBHasNothingAndGetGeolocationException(message: String) : MyRealmException(message)

    class DBHasOutdatedWeatherDataException(message: String, val isConnectedToInternet: Boolean) :
        MyRealmException(message)

    class DBHasNoWeatherResponseException(message: String, val isConnectedToInternet: Boolean) :
        MyRealmException(message)

}

sealed class GpsCallException(message: String) : Throwable(message) {

    class DeviceIsNotConnectedToInternetException(message: String) : GpsCallException(message)

    class HaveSuccessAndDeviceIsNotConnectedException(message: String) : GpsCallException(message)

    class HaveNotSuccessAndDeviceIsConnectedToInternet(message: String) : GpsCallException(message)

    class HaveNotSuccessAndDeviceIsNotConnectedToInternet(message: String) : GpsCallException(message)

    class UnknownErrorException(message: String) : GpsCallException(message)

}

class ExtractAddressException(message: String) : Throwable(message)

class GoogleGeocodeException(message: String) : Throwable(message)
