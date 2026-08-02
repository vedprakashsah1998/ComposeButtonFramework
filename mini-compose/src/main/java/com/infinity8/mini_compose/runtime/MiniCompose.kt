package com.infinity8.mini_compose.runtime

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.infinity8.mini_compose.ui.MiniComposeView

internal val LocalComposer =
    staticCompositionLocalOf<Composer> {
        error("No Composer found")
    }
@Composable

fun MiniCompose(
    modifier: Modifier = Modifier,
    content: @Composable Composer.() -> Unit
) {
    val composer = remember { Composer() }

    CompositionLocalProvider(
        LocalComposer provides composer
    ) {
        composer.beginComposition()
        composer.content()
    }
    AndroidView(
        modifier = modifier,
        factory = { context ->
            MiniComposeView(context)
        },
        update = { view ->
            view.setRoot(composer.build())
        }
    )
}