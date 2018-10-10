package ru.mts.avpopo85.weathery.presentation.base.withProgressBar

interface HasProgressBar {

    fun isLoadingProgressShown(): Boolean

    fun showLoadingProgress()

    fun hideLoadingProgress()

}