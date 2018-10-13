package ru.mts.avpopo85.weathery.data.model.implementation.common.googleGeocode.utils

enum class GoogleGeocodeStatus {
    OK,
    ZERO_RESULTS,
    OVER_DAILY_LIMIT,
    OVER_QUERY_LIMIT,
    REQUEST_DENIED,
    INVALID_REQUEST,
    UNKNOWN_ERROR
}