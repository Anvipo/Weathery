package ru.mts.avpopo85.weathery.data.model.implementation.common

import io.realm.RealmObject
import ru.mts.avpopo85.weathery.data.model.base.common.IUserLocale

open class UserLocale(

    override var language: String? = null,

    override var region: String? = null

) : IUserLocale, RealmObject()