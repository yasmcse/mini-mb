package com.moneybox.minimb.data.api

import okhttp3.Request
import retrofit2.Invocation

@Target(
    AnnotationTarget.FUNCTION,
    AnnotationTarget.PROPERTY_GETTER,
    AnnotationTarget.PROPERTY_SETTER
)
@Retention(AnnotationRetention.RUNTIME)
annotation class AuthenticationRequired


fun <T : Annotation> Request.getAnnotation(annotationClass: Class<T>): T? =
    this.tag(Invocation::class.java)?.method()?.getAnnotation(annotationClass)

fun Request.isAuthenticationRequired(): Boolean {
    return this.getAnnotation(AuthenticationRequired::class.java) != null
}