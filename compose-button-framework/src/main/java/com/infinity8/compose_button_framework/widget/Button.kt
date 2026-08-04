package com.infinity8.compose_button_framework.widget

import android.graphics.Color
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColorInt
import com.infinity8.compose_button_framework.layout.PaddingValues
import com.infinity8.compose_button_framework.modifier.Modifier
import com.infinity8.compose_button_framework.modifier.ModifierChain
import com.infinity8.compose_button_framework.node.Alignment
import com.infinity8.compose_button_framework.node.ButtonNode
import com.infinity8.compose_button_framework.node.ButtonStyle
import com.infinity8.compose_button_framework.runtime.LocalComposer

@Composable

fun Button(
    text: String,
    modifier: ModifierChain = Modifier,
    backgroundColor: Int = Color.BLACK,
    textColor: Int = Color.WHITE,
    enabled: Boolean = true,
    contentPadding: PaddingValues = PaddingValues(),
    textSize: Dp = 16.dp,
    cornerRadius: Dp = 16.dp,
    elevation: Dp = 0.dp,
    style: ButtonStyle = ButtonStyle.Filled,
    borderWidth: Dp = 0.dp,
    borderColor: Int = Color.BLACK,
    disabledBackgroundColor: Int = "#D1D1D6".toColorInt(),
    disabledBorderColor: Int = "#C7C7CC".toColorInt(),
    disabledTextColor: Int = "#8E8E93".toColorInt(),
    contentAlignment: Alignment = Alignment.Center,
    onClick: () -> Unit = {}
) {
    LocalComposer.current.emit(
        ButtonNode(
            text = text,
            modifier = modifier,
            backgroundColor = backgroundColor,
            textColor = textColor,
            enabled = enabled,
            contentPadding = contentPadding,
            cornerRadius = cornerRadius,
            textSize = textSize,
            elevation = elevation,
            style = style,
            borderWidth = borderWidth,
            borderColor = borderColor,
            disabledBackgroundColor = disabledBackgroundColor,
            disabledBorderColor = disabledBorderColor,
            disabledTextColor = disabledTextColor,
            contentAlignment = contentAlignment,
            onClick = onClick
        )
    )
}