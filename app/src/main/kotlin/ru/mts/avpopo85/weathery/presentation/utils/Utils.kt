package ru.mts.avpopo85.weathery.presentation.utils

import android.content.Context
import retrofit2.HttpException
import ru.mts.avpopo85.weathery.BuildConfig
import ru.mts.avpopo85.weathery.R
import ru.mts.avpopo85.weathery.presentation.base.BaseContract
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

fun onParameterIsNull(
    view: BaseContract.View?,
    className: String,
    methodName: String,
    parameterName: String
) {
    if (BuildConfig.DEBUG) {
        val message = "$className.$methodName - $parameterName == null"

        view?.sendErrorLog(message)

        view?.showError(message)
    }
}

fun Context.parseError(throwable: Throwable): String {
    val msg = when (throwable) {
        is HttpException -> {
            val code = throwable.code()

            val res =
                when (code) {
                    in 100..199 -> parse1xxHttpCode(code)
                    in 200..299 -> parse2xxHttpCode(code)
                    in 300..399 -> parse3xxHttpCode(code)
                    in 400..499 -> parse4xxHttpCode(code)
                    in 500..599 -> parse5xxHttpCode(code)
                    else -> "${getString(R.string.unknown_http_code)}. ${getString(R.string.please_try_later)}"
                }

            val url =
                if (code in 400..499 && code != 403)
                    " (${throwable.response().raw().request().url()})"
                else ""

            "$res$url"
        }
        is UnknownHostException -> "${getString(R.string.the_server_is_temporarily_unavailable)}. " +
                getString(R.string.please_try_later)
        is ConnectException -> getString(R.string.authorisation_error)
        is SocketTimeoutException -> "${getString(R.string.response_timeout)}. " +
                getString(R.string.make_sure_your_device_has_a_network_connection)
        else -> throwable.message!!
    }

    return if (throwable.message != null && msg.contains(throwable.message!!))
        msg
    else
        "$msg (${throwable.message})"
}

private fun Context.parse1xxHttpCode(code: Int): String = when (code) {
    100 -> getString(R.string._continue)
    101 -> getString(R.string.switching_protocols)
    102 -> getString(R.string.processing)
    else -> getString(R.string.unknown_informational_code_1xx)
}

private fun Context.parse2xxHttpCode(code: Int): String = when (code) {
    200 -> getString(R.string.ok)
    201 -> getString(R.string.created)
    202 -> getString(R.string.accepted)
    203 -> getString(R.string.non_authoritative_information)
    204 -> getString(R.string.no_content)
    205 -> getString(R.string.reset_content)
    206 -> getString(R.string.partial_content)
    207 -> getString(R.string.multi_status)
    208 -> getString(R.string.already_reported)
    226 -> getString(R.string.im_used)
    else -> getString(R.string.unknown_success_code_2xx)
}

private fun Context.parse3xxHttpCode(code: Int): String = when (code) {
    300 -> getString(R.string.multiple_choices)
    301 -> getString(R.string.moved_permanently)
    302 -> getString(R.string.found_or_moved_temporarily)
    303 -> getString(R.string.see_other)
    304 -> getString(R.string.not_modified)
    305 -> getString(R.string.use_proxy)
    306 -> ""
    307 -> getString(R.string.temporary_redirect)
    308 -> getString(R.string.permanent_redirect)
    else -> getString(R.string.unknown_error_3xx)
}

private fun Context.parse4xxHttpCode(code: Int): String = when (code) {
    400 -> getString(R.string.bad_request)
    401 -> getString(R.string.unauthorized)
    402 -> getString(R.string.payment_required)
    403 -> getString(R.string.forbidden)
    404 -> getString(R.string.not_found)
    405 -> getString(R.string.method_not_allowed)
    406 -> getString(R.string.not_acceptable)
    407 -> getString(R.string.proxy_authentication_required)
    408 -> getString(R.string.request_timeout)
    409 -> getString(R.string.conflict)
    410 -> getString(R.string.gone)
    411 -> getString(R.string.length_required)
    412 -> getString(R.string.precondition_failed)
    413 -> getString(R.string.payload_too_large)
    414 -> getString(R.string.uri_too_long)
    415 -> getString(R.string.unsupported_media_type)
    416 -> getString(R.string.range_not_satisfiable)
    417 -> getString(R.string.expectation_failed)
    418 -> getString(R.string.im_a_teapot)
    421 -> getString(R.string.misdirected_request)
    422 -> getString(R.string.unprocessable_entity)
    423 -> getString(R.string.locked)
    424 -> getString(R.string.failed_dependency)
    426 -> getString(R.string.upgrade_required)
    428 -> getString(R.string.precondition_required)
    429 -> getString(R.string.too_many_requests)
    431 -> getString(R.string.request_header_fields_too_large)
    434 -> getString(R.string.requested_host_unavailable)
    449 -> getString(R.string.retry_with)
    451 -> getString(R.string.unavailable_for_legal_reasons)
    else -> getString(R.string.unknown_error_4xx)
}

private fun Context.parse5xxHttpCode(code: Int): String = when (code) {
    500 -> getString(R.string.internal_server_error)
    501 -> getString(R.string.not_implemented)
    502 -> getString(R.string.bad_gateway)
    503 -> getString(R.string.service_unavailable)
    504 -> getString(R.string.gateway_timeout)
    505 -> getString(R.string.http_version_not_supported)
    506 -> getString(R.string.variant_also_negotiates)
    507 -> getString(R.string.insufficient_storage)
    508 -> getString(R.string.loop_detected)
    509 -> getString(R.string.bandwidth_limit_exceeded)
    510 -> getString(R.string.not_extended)
    511 -> getString(R.string.network_authentication_required)
    520 -> getString(R.string.unknown_error)
    521 -> getString(R.string.web_server_is_down)
    522 -> getString(R.string.connection_timed_out)
    523 -> getString(R.string.origin_is_unreachable)
    524 -> getString(R.string.a_timeout_occurred)
    525 -> getString(R.string.ssl_handshake_failed)
    526 -> getString(R.string.invalid_ssl_certificate)
    else -> getString(R.string.unknown_error_5xx)
}
