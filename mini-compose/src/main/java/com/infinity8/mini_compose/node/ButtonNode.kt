package com.infinity8.mini_compose.node

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColorInt

import com.infinity8.mini_compose.layout.Constraints
import com.infinity8.mini_compose.layout.MeasureResult
import com.infinity8.mini_compose.layout.PaddingValues
import com.infinity8.mini_compose.ui.unit.toPx

class ButtonNode(
    var text: String,

    var widthValue: Dp = 0.dp,
    var heightValue: Dp = 0.dp,

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
    val buttonAlignment: Alignment = Alignment.Start,

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

        val childResult = textNode.measure(constraints)

        width =
            if (widthValue.value > 0f)
                widthValue.toPx()
            else
                childResult.width +
                        contentPadding.start.toPx() +
                        contentPadding.end.toPx()

        height =
            if (heightValue.value > 0f)
                heightValue.toPx()
            else
                childResult.height +
                        contentPadding.top.toPx() +
                        contentPadding.bottom.toPx()

        return MeasureResult(width, height)
    }


    override fun layout() {

        val child = textNode

        child.x = (width - child.width) / 2f
        child.y = (height - child.height) / 2f
        when (contentAlignment) {

            Alignment.Start -> {
                child.x = x + contentPadding.start.toPx()
            }

            Alignment.Center -> {
                child.x = x + (width - child.width) / 2f
            }

            Alignment.End -> {
                child.x = x +
                        width -
                        child.width -
                        contentPadding.end.toPx()
            }
        }

        child.y = y + (height - child.height) / 2f

        child.layout()
    }

    override fun draw(canvas: Canvas) {
        val elevationPx = elevation.toPx()
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
                x,
                y,
                x + width,
                y + height,
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
            x,
            y,
            x + width,
            y + height,
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