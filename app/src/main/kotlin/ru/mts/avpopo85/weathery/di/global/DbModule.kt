package ru.mts.avpopo85.weathery.di.global

import dagger.Module
import dagger.Provides
import io.realm.Realm
import ru.mts.avpopo85.weathery.data.db.RealmService

@Module
class DbModule {

    @Provides
    fun provideRealm(): Realm = Realm.getDefaultInstance()

    @Provides
    fun provideRealmService(realm: Realm): RealmService = RealmService(realm)

}