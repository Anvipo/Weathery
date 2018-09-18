package ru.mts.avpopo85.weathery.di.global

import dagger.Module
import dagger.Provides
import io.reactivex.CompletableTransformer
import io.reactivex.MaybeTransformer
import io.reactivex.ObservableTransformer
import io.reactivex.SingleTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

@Suppress("unused")
@Module
class SchedulerManagerModule {

    @Provides
    fun provideSchedulerMangerModule(): SchedulerManagerModule = SchedulerManagerModule()

    fun <T> observableTransformer(): ObservableTransformer<T, T> =
        ObservableTransformer { observable ->
            (observable)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
        }

    fun <T> singleTransformer(): SingleTransformer<T, T> = SingleTransformer { single ->
        (single)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun <T> maybeTransformer(): MaybeTransformer<T, T> = MaybeTransformer { maybe ->
        (maybe)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun completableTransformer(): CompletableTransformer = CompletableTransformer { completable ->
        (completable)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

}