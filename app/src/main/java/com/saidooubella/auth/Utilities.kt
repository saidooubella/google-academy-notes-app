package com.saidooubella.auth

import android.app.Activity
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.os.Handler
import android.view.LayoutInflater
import android.view.Window
import android.widget.Toast
import androidx.annotation.ColorInt
import androidx.core.graphics.ColorUtils
import androidx.core.view.WindowInsetsControllerCompat

internal fun <T : Any> Activity.binding(builder: (LayoutInflater) -> T) =
    lazy(LazyThreadSafetyMode.NONE) { builder(layoutInflater) }

internal fun Handler.postDelayed(delay: Long, runnable: Runnable) =
    postDelayed(runnable, delay)

internal fun Window.setSystemBarsLighting() {
    val controllerCompat = WindowInsetsControllerCompat(this, decorView)
    val isLight = (decorView.background as ColorDrawable).color.isLight()
    controllerCompat.isAppearanceLightNavigationBars = isLight
    controllerCompat.isAppearanceLightStatusBars = isLight
}

internal fun @receiver:ColorInt Int.isLight(): Boolean {
    return ColorUtils.calculateLuminance(this) > 0.5
}

internal fun Context.toast(message: String, length: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, length).show()
}
