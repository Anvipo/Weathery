package ru.mts.avpopo85.weathery.data.utils

enum class YandexWeatherLanguages(val lang_code: String) {
    RU_RU("ru_RU"),
    RU_UA("ru_UA"),
    UK_UA("uk_UA"),
    BE_BY("be_BY"),
    KK_KZ("kk_KZ"),
    TR_TR("tr_TR"),
    EN_US("en_US")
}

enum class OpenWeatherMapLanguages(val lang_code: String) {
    ARABIC("ar"),
    BULGARIAN("bg"),
    CATALAN("ca"),
    CZECH("cz"),
    German("de"),
    GREEK("el"),
    ENGLISH("en"),
    PERSIAN_FARSI("fa"),
    FINNISH("fi"),
    FRENCH("fr"),
    GALICIAN("gl"),
    CROATIAN("hr"),
    HUNGARIAN("hu"),
    ITALIAN("it"),
    JAPANESE("ja"),
    KOREAN("kr"),
    LATVIAN("la"),
    LITHUANIAN("lt"),
    MACEDONIAN("mk"),
    DUTCH("nl"),
    POLISH("pl"),
    PORTUGUESE("pt"),
    ROMANIAN("ro"),
    RUSSIAN("ru"),
    SWEDISH("se"),
    SLOVAK("sk"),
    SLOVENIAN("sl"),
    SPANISH("es"),
    TURKISH("tr"),
    UKRAINIAN("ua"),
    VIETNAMESE("vi"),
    CHINESE_SIMPLIFIED("zh_cn"),
    CHINESE_TRADITIONAL("zh_tw")
}

enum class OWMUnitsFormat(val temperature: String) {
    STANDARD("Kelvin"),
    METRIC("Celsius"),
    IMPERIAL ("Fahrenheit")
}