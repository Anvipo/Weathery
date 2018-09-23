package ru.mts.avpopo85.weathery.presentation.utils

import android.content.Context
import retrofit2.HttpException
import ru.mts.avpopo85.weathery.R
import ru.mts.avpopo85.weathery.utils.yandexWeather.YWForecastType
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

fun Context.parseError(throwable: Throwable): String {
    val msg = when (throwable) {
        is HttpException -> {
            val code = throwable.code()

            when (code) {
                400 -> "Запрос невалидный."
                401 -> "В запросе не указаны авторизационные данные."
                403 -> "Доступ запрещён."
                404 -> "Запрашиваемый ресурс не найден."
                405 -> "Запрашиваемый метод для указанного ресурса не поддерживается."
                415 -> "Запрашиваемый тип контента не поддерживается методом."
                420 -> "Превышено ограничение на доступ к ресурсу."
                500 -> "Внутренняя ошибка сервера. Пожалуйста, попробуйте позже."
                503 -> "Сервер временно недоступен из-за высокой загрузки."
                else -> "Произошла непредвиденная ошибка сервера. Пожалуйста, попробуйте позже."
            }
        }
        is UnknownHostException -> "Сервер временно недоступен. Пожалуйста, попробуйте позже."
        is ConnectException -> "Ошибка авторизации"
        is SocketTimeoutException -> "Время ожидания ответа истекло. Убедитесь, что на устройсте есть подключение к сети."
        else -> throwable.message!!
    }

    return if (throwable.message != null && msg.contains(throwable.message!!))
        msg
    else
        "$msg (${throwable.message})."
}

fun Context.makeTitle(YWForecast: YWForecastType): String =
    "${YWForecast.dateInUnixtime} (${YWForecast.weekSerialNumber} ${getString(R.string.week)})"