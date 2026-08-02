package com.infinity8.mini_compose.ui.unit

import android.content.res.Resources
import androidx.compose.ui.unit.Dp

fun Dp.toPx(): Float = value * Resources.getSystem().displayMetrics.density