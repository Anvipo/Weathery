package ru.mts.avpopo85.weathery.data.utils

import java.util.Date

fun Long.isFreshThan(cacheLifetime: Long): Boolean =
    Date().time - this < cacheLifetime