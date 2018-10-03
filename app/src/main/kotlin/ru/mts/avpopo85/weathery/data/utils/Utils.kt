package ru.mts.avpopo85.weathery.data.utils

import ru.mts.avpopo85.weathery.data.model.implementation.common.UserAddress
import java.util.*

fun Long.isFresh(cacheLifetime: Long): Boolean = Date().time - this < cacheLifetime

typealias UserAddressType = UserAddress