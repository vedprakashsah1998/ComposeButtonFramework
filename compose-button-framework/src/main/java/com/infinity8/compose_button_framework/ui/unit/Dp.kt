package com.infinity8.compose_button_framework.ui.unit

import android.content.res.Resources
import androidx.compose.ui.unit.Dp

fun Dp.toPx(): Float = value * Resources.getSystem().displayMetrics.density