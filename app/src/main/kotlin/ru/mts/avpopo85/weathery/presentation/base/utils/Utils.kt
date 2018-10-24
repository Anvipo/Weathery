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
    params.forEach {
        //todo
        val value = it.second
        when (value) {
            null -> intent.putExtra(it.first, null as Serializable?)
            is Boolean -> intent.putExtra(it.first, value)
            is Byte -> intent.putExtra(it.first, value)
            is Char -> intent.putExtra(it.first, value)
            is Short -> intent.putExtra(it.first, value)
            is Int -> intent.putExtra(it.first, value)
            is Long -> intent.putExtra(it.first, value)
            is Float -> intent.putExtra(it.first, value)
            is Double -> intent.putExtra(it.first, value)
            is String -> intent.putExtra(it.first, value)
            is CharSequence -> intent.putExtra(it.first, value)
            is Parcelable -> intent.putExtra(it.first, value)
            is Serializable -> intent.putExtra(it.first, value)
            is BooleanArray -> intent.putExtra(it.first, value)
            is ByteArray -> intent.putExtra(it.first, value)
            is ShortArray -> intent.putExtra(it.first, value)
            is CharArray -> intent.putExtra(it.first, value)
            is IntArray -> intent.putExtra(it.first, value)
            is LongArray -> intent.putExtra(it.first, value)
            is FloatArray -> intent.putExtra(it.first, value)
            is DoubleArray -> intent.putExtra(it.first, value)
            is Array<*> -> when {
                value.isArrayOf<CharSequence>() -> intent.putExtra(it.first, value)
                value.isArrayOf<String>() -> intent.putExtra(it.first, value)
                value.isArrayOf<Parcelable>() -> intent.putExtra(it.first, value)
                else -> throw RuntimeException("Intent extra ${it.first} has wrong type ${value.javaClass.name}")
            }
            is Bundle -> intent.putExtra(it.first, value)
            else -> throw RuntimeException("Intent extra ${it.first} has wrong type ${value.javaClass.name}")
        }
    }
}