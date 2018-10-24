package ru.mts.avpopo85.weathery.data.model.implementation.common

import android.os.Parcelable
import io.realm.RealmObject
import kotlinx.android.parcel.Parcelize
import ru.mts.avpopo85.weathery.data.model.base.common.IUserLocale

@Parcelize
open class UserLocale(

    override var language: String? = null,

    override var region: String? = null

) : RealmObject(), IUserLocale, Parcelable
