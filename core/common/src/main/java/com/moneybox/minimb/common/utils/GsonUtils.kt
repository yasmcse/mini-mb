package com.moneybox.minimb.common.utils

import com.google.gson.Gson
import java.lang.reflect.Type

fun getDataClassAsString(
    dataClass: Any?
): String {
    val gson = Gson()
    return gson.toJson(dataClass)
}

fun <T> getStringAsDataClass(
    dataClass: String,
    classType: Type
): T? {
    val gson = Gson()
    return gson.fromJson(dataClass, classType)
}