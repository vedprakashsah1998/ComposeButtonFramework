package com.infinity8.compose_button_framework.node

import android.animation.ValueAnimator
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Paint
import android.graphics.RadialGradient
import android.graphics.Shader
import android.graphics.SweepGradient
import android.view.animation.DecelerateInterpolator
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColorInt
import com.infinity8.compose_button_framework.FontFamily
import com.infinity8.compose_button_framework.FontWeight
import com.infinity8.compose_button_framework.TextOverflow
import com.infinity8.compose_button_framework.extension.resolvePadding
import com.infinity8.compose_button_framework.extension.resolveSize
import com.infinity8.compose_button_framework.gradient.ButtonGradient

import com.infinity8.compose_button_framework.layout.Constraints
import com.infinity8.compose_button_framework.layout.MeasureResult
import com.infinity8.compose_button_framework.layout.PaddingValues
import com.infinity8.compose_button_framework.modifier.Modifier
import com.infinity8.compose_button_framework.modifier.ModifierChain
import com.infinity8.compose_button_framework.modifier.SizeValue
import com.infinity8.compose_button_framework.resource.FontFamilyResolver
import com.infinity8.compose_button_framework.resource.FontStyle
import com.infinity8.compose_button_framework.ui.unit.toPx

class ButtonNode(
    var text: String,
    val modifier: ModifierChain = Modifier,
    var backgroundColor: Int = Color.BLACK,
    var textColor: Int = Color.WHITE,

    var enabled: Boolean = true,
    val contentPadding: PaddingValues = PaddingValues(),

    val cornerRadius: Dp = 16.dp,
    var textSize: Dp = 16.dp,

    val elevation: Dp = 0.dp,

    val style: ButtonStyle = ButtonStyle.Filled,

    val borderWidth: Dp = 0.dp,
    val borderColor: Int = Color.BLACK,
    val disabledBackgroundColor: Int = "#D1D1D6".toColorInt(),
    val disabledBorderColor: Int = "#C7C7CC".toColorInt(),
    val disabledTextColor: Int = "#8E8E93".toColorInt(),
    val contentAlignment: Alignment = Alignment.Center,
    val fontResolver: FontFamilyResolver? = null,
    val gradient: ButtonGradient? = null,

    var fontFamily: FontFamily = FontFamily.Default,
    var fontStyle: FontStyle = FontStyle.Normal,
    var fontWeight: FontWeight = FontWeight.Normal,

    var maxLines: Int = Int.MAX_VALUE,

    var overflow: TextOverflow = TextOverflow.Clip,

    var isUpperCase: Boolean = false,
    var onClick: () -> Unit = {}
) : LayoutNode() {

    private var isPressed = false

    private var currentScale = 1f
    private var pressAnimator: ValueAnimator? = null
    private var currentAlpha = 1f
    private val textNode = TextNode(
        text = text,
        textColor = textColor,
        textSize = textSize,
        fontFamily = fontFamily,
        fontWeight = fontWeight,
        fontResolver = fontResolver,
        maxLines = maxLines,
        overflow = overflow,
        isUpperCase = isUpperCase
    )

    private fun updateTextNode() {
        textNode.textSize = textSize
        textNode.text = text
        textNode.textColor =
            if (enabled) textColor else disabledTextColor

        textNode.fontFamily = fontFamily
        textNode.fontWeight = fontWeight
        textNode.fontStyle = fontStyle
        textNode.maxLines = maxLines
        textNode.overflow = overflow
        textNode.isUpperCase = isUpperCase
        textNode.alpha = currentAlpha
        textNode.textAlignment = contentAlignment

    }

    private fun animateTo(
        targetScale: Float,
        targetAlpha: Float,
        duration: Long
    ) {
        pressAnimator?.cancel()

        val startScale = currentScale
        val startAlpha = currentAlpha

        pressAnimator = ValueAnimator.ofFloat(0f, 1f).apply {

            this.duration = duration

            interpolator = DecelerateInterpolator()

            addUpdateListener { animator ->

                val progress = animator.animatedValue as Float

                currentScale =
                    startScale +
                            (targetScale - startScale) * progress

                currentAlpha =
                    startAlpha +
                            (targetAlpha - startAlpha) * progress

                requestRedraw()
            }

            start()
        }
    }

    private fun createGradientShader(
        gradient: ButtonGradient,
        buttonX: Float,
        buttonY: Float,
        buttonWidth: Float,
        buttonHeight: Float
    ): Shader {

        return when (gradient) {

            is ButtonGradient.Horizontal -> {

                LinearGradient(
                    buttonX + buttonWidth * gradient.startX,
                    buttonY,
                    buttonX + buttonWidth * gradient.endX,
                    buttonY,
                    gradient.colors.toIntArray(),
                    null,
                    Shader.TileMode.CLAMP
                )
            }

            is ButtonGradient.Vertical -> {

                LinearGradient(
                    buttonX,
                    buttonY + buttonHeight * gradient.startY,
                    buttonX,
                    buttonY + buttonHeight * gradient.endY,
                    gradient.colors.toIntArray(),
                    null,
                    Shader.TileMode.CLAMP
                )
            }

            is ButtonGradient.Diagonal -> {

                LinearGradient(
                    buttonX + buttonWidth * gradient.startX,
                    buttonY + buttonHeight * gradient.startY,
                    buttonX + buttonWidth * gradient.endX,
                    buttonY + buttonHeight * gradient.endY,
                    gradient.colors.toIntArray(),
                    null,
                    Shader.TileMode.CLAMP
                )
            }

            is ButtonGradient.Radial -> {

                RadialGradient(
                    buttonX + buttonWidth * gradient.centerX,
                    buttonY + buttonHeight * gradient.centerY,
                    minOf(buttonWidth, buttonHeight) * gradient.radius,
                    gradient.colors.toIntArray(),
                    null,
                    Shader.TileMode.CLAMP
                )
            }

            is ButtonGradient.Sweep -> {

                SweepGradient(
                    buttonX + buttonWidth * gradient.centerX,
                    buttonY + buttonHeight * gradient.centerY,
                    gradient.colors.toIntArray(),
                    null
                )
            }
        }
    }

    private val shadowPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
    }

    init {
        addChild(textNode)
    }

    private val backgroundPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
    }

    override fun onTouchDown(
        x: Float,
        y: Float
    ): Boolean {

        if (!enabled) {
            return false
        }

        onPress()

        return true
    }

    override fun onTouchUp(
        x: Float,
        y: Float
    ): Boolean {

        if (!enabled) {
            return false
        }

        onRelease()

        return true
    }

    override fun onTouchCancel(): Boolean {

        if (!enabled) {
            return false
        }

        onCancel()

        return true
    }

    fun onPress() {

        if (!enabled) return

        isPressed = true

        animateTo(
            targetScale = 0.96f,
            targetAlpha = 0.92f,
            duration = 100L
        )
    }

    fun onRelease() {

        if (!enabled) return

        isPressed = false

        animateTo(
            targetScale = 1f,
            targetAlpha = 1f,
            duration = 160L
        )

        onClick.invoke()
    }

    fun onCancel() {

        if (!enabled) return

        isPressed = false

        animateTo(
            targetScale = 1f,
            targetAlpha = 1f,
            duration = 120L
        )
    }

    override fun measure(
        constraints: Constraints
    ): MeasureResult {
        updateTextNode()
        val size = modifier.resolveSize()
        val outerPadding = modifier.resolvePadding()

        val maxTextWidth = (
                constraints.maxWidth
                        - outerPadding.start.toPx()
                        - outerPadding.end.toPx()
                        - contentPadding.start.toPx()
                        - contentPadding.end.toPx()
                ).coerceAtLeast(0f)

        textNode.availableWidth = maxTextWidth

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
    override fun findTouchTarget(
        x: Float,
        y: Float
    ): LayoutNode? {

        return if (
            enabled &&
            contains(x, y)
        ) {
            this
        } else {
            null
        }
    }
    override fun draw(canvas: Canvas) {
        val elevationPx = elevation.toPx()
        val outerPadding = modifier.resolvePadding()

        val buttonX = x + outerPadding.start.toPx()
        val buttonY = y + outerPadding.top.toPx()
        canvas.save()


        val buttonWidth =
            width -
                    outerPadding.start.toPx() -
                    outerPadding.end.toPx()

        val buttonHeight =
            height -
                    outerPadding.top.toPx() -
                    outerPadding.bottom.toPx()
        val centerX = buttonX + buttonWidth / 2f
        val centerY = buttonY + buttonHeight / 2f
        canvas.scale(
            currentScale,
            currentScale,
            centerX,
            centerY
        )
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
                if (enabled && gradient != null) {
                    backgroundPaint.shader = createGradientShader(
                        gradient = gradient,
                        buttonX = buttonX,
                        buttonY = buttonY,
                        buttonWidth = buttonWidth,
                        buttonHeight = buttonHeight
                    )
                } else {
                    backgroundPaint.shader = null
                    backgroundPaint.color =
                        if (enabled)
                            backgroundColor
                        else
                            disabledBackgroundColor
                }
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
            (255 * currentAlpha)
                .toInt()
                .coerceIn(0, 255)

        canvas.drawRoundRect(
            buttonX,
            buttonY,
            buttonX + buttonWidth,
            buttonY + buttonHeight,
            cornerRadius.toPx(),
            cornerRadius.toPx(),
            backgroundPaint
        )
        textNode.alpha = currentAlpha

        backgroundPaint.shader = null
        textNode.draw(canvas)
        canvas.restore()
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