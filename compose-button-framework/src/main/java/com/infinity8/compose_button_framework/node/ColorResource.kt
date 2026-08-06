package com.infinity8.compose_button_framework.node

import androidx.annotation.ColorRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalResources
import androidx.core.content.res.ResourcesCompat


@Composable
@ReadOnlyComposable
fun colorResource(
    @ColorRes id: Int
): Int {
    val context = LocalContext.current

    return ResourcesCompat.getColor(
        LocalResources.current,
        id,
        context.theme
    )
}