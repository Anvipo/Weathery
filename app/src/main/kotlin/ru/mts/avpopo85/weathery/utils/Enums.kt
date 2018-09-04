package ru.mts.avpopo85.weathery.utils

enum class PossibleUnits {
    METRIC, STANDARD, IMPERIAL
}

enum class YandexWeatherLanguages(val lang_code: String) {
    RU_RU("ru_RU"),
    RU_UA("ru_UA"),
    UK_UA("uk_UA"),
    BE_BY("be_BY"),
    KK_KZ("kk_KZ"),
    TR_TR("tr_TR"),
    EN_US("en_US")
}