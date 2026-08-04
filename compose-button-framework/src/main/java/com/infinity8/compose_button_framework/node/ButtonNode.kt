package com.infinity8.compose_button_framework.node

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColorInt
import com.infinity8.compose_button_framework.extension.resolvePadding
import com.infinity8.compose_button_framework.extension.resolveSize

import com.infinity8.compose_button_framework.layout.Constraints
import com.infinity8.compose_button_framework.layout.MeasureResult
import com.infinity8.compose_button_framework.layout.PaddingValues
import com.infinity8.compose_button_framework.modifier.Modifier
import com.infinity8.compose_button_framework.modifier.ModifierChain
import com.infinity8.compose_button_framework.modifier.SizeValue
import com.infinity8.compose_button_framework.ui.unit.toPx

class ButtonNode(
    var text: String,
     val modifier: ModifierChain = Modifier,
    var backgroundColor: Int = Color.BLACK,
    var textColor: Int = Color.WHITE,

    var enabled: Boolean = true,
    val contentPadding: PaddingValues = PaddingValues(),

    val cornerRadius: Dp = 16.dp,
    val textSize: Dp = 16.dp,

    val elevation: Dp = 0.dp,

    val style: ButtonStyle = ButtonStyle.Filled,

    val borderWidth: Dp = 0.dp,
    val borderColor: Int = Color.BLACK,
    val disabledBackgroundColor: Int = "#D1D1D6".toColorInt(),
    val disabledBorderColor: Int = "#C7C7CC".toColorInt(),
    val disabledTextColor: Int = "#8E8E93".toColorInt(),
    val contentAlignment: Alignment = Alignment.Center,

    var onClick: () -> Unit = {}
) : LayoutNode() {

    private var isPressed = false

    private var currentScale = 1f

    private var currentAlpha = 1f
    private val textNode = TextNode(
        text,
        textColor = textColor,
        textSize = textSize
    )
    private val shadowPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
    }

    init {
        addChild(textNode)
    }

    private val backgroundPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
    }

    fun onPress() {

        if (!enabled) return

        isPressed = true
        currentScale = 0.96f
        currentAlpha = 0.85f
    }

    fun onRelease() {

        if (!enabled) return

        isPressed = false
        currentScale = 1f
        currentAlpha = 1f

        onClick.invoke()
    }

    fun onCancel() {

        isPressed = false
        currentScale = 1f
        currentAlpha = 1f
    }

    override fun measure(
        constraints: Constraints
    ): MeasureResult {

        val size = modifier.resolveSize()
        val outerPadding = modifier.resolvePadding()

        val childResult = textNode.measure(constraints)
        val buttonWidth =
            childResult.width +
                    contentPadding.start.toPx() +
                    contentPadding.end.toPx()

        val buttonHeight =
            childResult.height +
                    contentPadding.top.toPx() +
                    contentPadding.bottom.toPx()
        width = when (val value = size.width) {

            is SizeValue.Fixed ->
                value.dp.toPx()

            SizeValue.FillMax ->
                constraints.maxWidth

            is SizeValue.FillFraction ->
                constraints.maxWidth * value.fraction

            SizeValue.WrapContent, null ->
                buttonWidth +
                        outerPadding.start.toPx() +
                        outerPadding.end.toPx()
        }

        height = when (val value = size.height) {

            is SizeValue.Fixed ->
                value.dp.toPx()

            SizeValue.FillMax ->
                constraints.maxHeight

            is SizeValue.FillFraction ->
                constraints.maxHeight * value.fraction

            SizeValue.WrapContent, null ->
                buttonHeight +
                        outerPadding.top.toPx() +
                        outerPadding.bottom.toPx()
        }


        return MeasureResult(width, height)
    }


    override fun layout() {

        val child = textNode
        val outerPadding = modifier.resolvePadding()

        val buttonX = x + outerPadding.start.toPx()
        val buttonY = y + outerPadding.top.toPx()
        val buttonWidth =
            width -
                    outerPadding.start.toPx() -
                    outerPadding.end.toPx()

        val buttonHeight =
            height -
                    outerPadding.top.toPx() -
                    outerPadding.bottom.toPx()

        when (contentAlignment) {

            Alignment.Start -> {
                child.x = buttonX + contentPadding.start.toPx()
            }

            Alignment.Center -> {
                child.x = buttonX + (buttonWidth - child.width) / 2f
            }

            Alignment.End -> {
                child.x =
                    buttonX +
                            buttonWidth -
                            child.width -
                            contentPadding.end.toPx()
            }
        }

        child.y = buttonY + (buttonHeight - child.height) / 2f
        child.layout()
    }

    override fun draw(canvas: Canvas) {
        val elevationPx = elevation.toPx()
        val outerPadding = modifier.resolvePadding()

        val buttonX = x + outerPadding.start.toPx()
        val buttonY = y + outerPadding.top.toPx()

        val buttonWidth =
            width -
                    outerPadding.start.toPx() -
                    outerPadding.end.toPx()

        val buttonHeight =
            height -
                    outerPadding.top.toPx() -
                    outerPadding.bottom.toPx()
        textNode.textColor =
            if (enabled)
                textColor
            else
                disabledTextColor

        textNode.alpha = currentAlpha
        if (elevationPx > 0f) {

            // Convert elevation into shadow properties
            val shadowOffsetY = elevationPx * 0.75f
            val shadowBlur = elevationPx * 2f
            val shadowAlpha = (elevationPx * 8).coerceAtMost(60f).toInt()

            shadowPaint.color = Color.argb(
                shadowAlpha,
                0,
                0,
                0
            )

            shadowPaint.setShadowLayer(
                shadowBlur,
                0f,
                shadowOffsetY,
                shadowPaint.color
            )

            canvas.drawRoundRect(
                buttonX,
                buttonY,
                buttonX + buttonWidth,
                buttonY + buttonHeight,
                cornerRadius.toPx(),
                cornerRadius.toPx(),
                shadowPaint
            )

            shadowPaint.clearShadowLayer()
        }


        when (style) {
            ButtonStyle.Filled -> {
                backgroundPaint.style = Paint.Style.FILL
                backgroundPaint.color =
                    if (enabled)
                        backgroundColor
                    else
                        disabledBackgroundColor
            }

            ButtonStyle.Outlined -> {
                backgroundPaint.style = Paint.Style.STROKE
                backgroundPaint.strokeWidth = borderWidth.toPx()
                backgroundPaint.color =
                    if (enabled)
                        borderColor
                    else
                        disabledBorderColor
            }
        }
        backgroundPaint.alpha =
            (255 * currentAlpha).toInt()
        canvas.drawRoundRect(
            buttonX,
            buttonY,
            buttonX + buttonWidth,
            buttonY + buttonHeight,
            cornerRadius.toPx(),
            cornerRadius.toPx(),
            backgroundPaint
        )
        backgroundPaint.clearShadowLayer()

        textNode.draw(canvas)

    }
}

enum class ButtonStyle {
    Filled,
    Outlined
}

enum class Alignment {
    Start,
    Center,
    End
}