package ru.mts.avpopo85.weathery.utils.common

sealed class MyRealmException(message: String) : Throwable(message) {

    class DBHasNoCurrentAddressException(message: String) : MyRealmException(message)

    class DBHasOutdatedWeatherDataException(message: String, val isConnectedToInternet: Boolean) :
        MyRealmException(message)

    class DBHasNoWeatherResponseException(message: String, val isConnectedToInternet: Boolean) :
        MyRealmException(message)

}

sealed class GpsCallException(message: String) : Throwable(message) {

    class HaveSuccessAndDeviceIsNotConnectedException(message: String) : GpsCallException(message)

    class HaveNotSuccessAndDeviceIsConnectedToInternetException(message: String) :
        GpsCallException(message)

    class HaveNotSuccessAndDeviceIsNotConnectedToInternetException(message: String) :
        GpsCallException(message)

}

class DeviceIsNotConnectedToInternetException(message: String? = null) : Throwable(message)

class ExtractAddressException(message: String? = null) : Throwable(message)

class UnknownErrorException(message: String, throwable: Throwable? = null) : Throwable(message)
