package ru.mts.avpopo85.weathery.presentation.base.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import java.io.Serializable

fun startActivity(
    context: Context,
    clazz: Class<out Activity>,
    params: Array<out Pair<String, Any?>>
) {
    val intent = createIntent(context, clazz, params)
    context.startActivity(intent)
}

fun startActivityForResult(
    activity: Activity,
    clazz: Class<out Activity>,
    requestCode: Int,
    params: Array<out Pair<String, Any?>>
) {
    val intent = createIntent(activity, clazz, params)
    activity.startActivityForResult(intent, requestCode)
}

private fun createIntent(
    context: Context,
    clazz: Class<out Activity>,
    params: Array<out Pair<String, Any?>>
): Intent {
    val intent = Intent(context, clazz)
    if (params.isNotEmpty()) fillIntentArguments(intent, params)
    return intent
}

private fun fillIntentArguments(intent: Intent, params: Array<out Pair<String, Any?>>) {
    for ((name, value) in params) {
        when (value) {
            null -> intent.putExtra(name, null as Serializable?)
            is Boolean -> intent.putExtra(name, value)
            is Byte -> intent.putExtra(name, value)
            is Char -> intent.putExtra(name, value)
            is Short -> intent.putExtra(name, value)
            is Int -> intent.putExtra(name, value)
            is Long -> intent.putExtra(name, value)
            is Float -> intent.putExtra(name, value)
            is Double -> intent.putExtra(name, value)
            is String -> intent.putExtra(name, value)
            is CharSequence -> intent.putExtra(name, value)
            is Parcelable -> intent.putExtra(name, value)
            is Serializable -> intent.putExtra(name, value)
            is BooleanArray -> intent.putExtra(name, value)
            is ByteArray -> intent.putExtra(name, value)
            is ShortArray -> intent.putExtra(name, value)
            is CharArray -> intent.putExtra(name, value)
            is IntArray -> intent.putExtra(name, value)
            is LongArray -> intent.putExtra(name, value)
            is FloatArray -> intent.putExtra(name, value)
            is DoubleArray -> intent.putExtra(name, value)
            is Array<*> -> when {
                value.isArrayOf<CharSequence>() -> intent.putExtra(name, value)
                value.isArrayOf<String>() -> intent.putExtra(name, value)
                value.isArrayOf<Parcelable>() -> intent.putExtra(name, value)
                else -> throw RuntimeException("Intent extra $name has wrong type ${value.javaClass.name}")
            }
            is Bundle -> intent.putExtra(name, value)
            else -> throw RuntimeException("Intent extra $name has wrong type ${value.javaClass.name}")
        }
    }
}