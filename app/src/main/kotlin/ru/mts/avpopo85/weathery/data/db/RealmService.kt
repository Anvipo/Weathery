package ru.mts.avpopo85.weathery.data.db

import io.realm.Realm


class RealmService(private val realm: Realm) {

    fun closeRealm() {
        realm.close()
    }

}