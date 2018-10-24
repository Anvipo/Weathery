package ru.mts.avpopo85.weathery.presentation.welcome.base

import ru.mts.avpopo85.weathery.presentation.base.withProgressBar.BaseProgressBarContract

interface WelcomeContract : BaseProgressBarContract {

    interface View : BaseProgressBarContract.View

    interface Presenter : BaseProgressBarContract.Presenter<View>

}