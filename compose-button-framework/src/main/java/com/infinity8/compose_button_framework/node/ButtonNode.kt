package com.infinity8.compose_button_framework.node

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Paint
import android.graphics.RadialGradient
import android.graphics.Shader
import android.graphics.SweepGradient
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
import com.infinity8.compose_button_framework.modifier.SizeModifier
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
    private var currentAlpha = 1f
    private val shadowPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
    }

    // Cached shadow values.
    private var shadowAlpha = 0
    private var shadowBlurPx = 0f
    private var shadowOffsetYPx = 0f
    private var animationStartTimeNanos = 0L
    private var animationDurationNanos = 0L

    private var animationStartScale = 1f
    private var animationTargetScale = 1f

    private var animationStartAlpha = 1f
    private var animationTargetAlpha = 1f

    private var isAnimating = false

    private val backgroundPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
    }

    // Cached button geometry in px.
    private var buttonX = 0f
    private var buttonY = 0f
    private var buttonWidth = 0f
    private var buttonHeight = 0f

    private var centerX = 0f
    private var centerY = 0f

    // Cached visual values in px.
    private var cornerRadiusPx = 0f
    private var borderWidthPx = 0f
    private var elevationPx = 0f

    // Cached gradient.
    private var cachedGradient: ButtonGradient? = null
    private var cachedGradientWidth = 0f
    private var cachedGradientHeight = 0f
    private var cachedGradientShader: Shader? = null

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

    init {
        addChild(textNode)
    }

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
        durationMillis: Long
    ) {

        animationStartScale = currentScale
        animationTargetScale = targetScale

        animationStartAlpha = currentAlpha
        animationTargetAlpha = targetAlpha

        animationDurationNanos =
            durationMillis * 1_000_000L

        animationStartTimeNanos =
            System.nanoTime()

        isAnimating = true

        requestRedraw()
    }

    override fun updateAnimations(
        frameTimeNanos: Long
    ): Boolean {

        if (!isAnimating) {
            return super.updateAnimations(
                frameTimeNanos
            )
        }

        val elapsedNanos =
            frameTimeNanos -
                    animationStartTimeNanos

        val progress =
            if (animationDurationNanos <= 0L) {
                1f
            } else {
                (elapsedNanos.toFloat() / animationDurationNanos.toFloat()).coerceIn(0f, 1f)
            }

        val easedProgress =
            easeOutCubic(progress)

        currentScale =
            animationStartScale +
                    (
                            animationTargetScale -
                                    animationStartScale
                            ) * easedProgress

        currentAlpha =
            animationStartAlpha +
                    (
                            animationTargetAlpha -
                                    animationStartAlpha
                            ) * easedProgress

        if (progress >= 1f) {
            currentScale = animationTargetScale

            currentAlpha = animationTargetAlpha

            isAnimating = false
        }

        textNode.alpha = currentAlpha

        val childAnimating =
            super.updateAnimations(
                frameTimeNanos
            )

        return isAnimating || childAnimating
    }

    private fun easeOutCubic(
        progress: Float
    ): Float {

        val inverse =
            1f - progress

        return 1f -
                inverse * inverse * inverse
    }

    private fun getGradientShader(): Shader? {

        val currentGradient = gradient
            ?: return null

        if (
            cachedGradient === currentGradient &&
            cachedGradientWidth == buttonWidth &&
            cachedGradientHeight == buttonHeight
        ) {
            return cachedGradientShader
        }

        val shader = createGradientShader(
            gradient = currentGradient,
            buttonX = buttonX,
            buttonY = buttonY,
            buttonWidth = buttonWidth,
            buttonHeight = buttonHeight
        )

        cachedGradient = currentGradient
        cachedGradientWidth = buttonWidth
        cachedGradientHeight = buttonHeight
        cachedGradientShader = shader

        return shader
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
            targetScale = 0.97f,
            targetAlpha = 0.94f,
            durationMillis = 50L
        )
    }

    fun onRelease() {

        if (!enabled) return

        isPressed = false

        animateTo(
            targetScale = 1f,
            targetAlpha = 1f,
            durationMillis = 120L
        )

        onClick.invoke()
    }

    fun onCancel() {

        if (!enabled) return

        isPressed = false

        animateTo(
            targetScale = 1f,
            targetAlpha = 1f,
            durationMillis = 90L
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
            childResult.width + contentPadding.start.toPx() + contentPadding.end.toPx()

        val buttonHeight =
            childResult.height + contentPadding.top.toPx() + contentPadding.bottom.toPx()

        width = buttonWidthImpl(size, constraints, buttonWidth, outerPadding)

        height = buttonHeightImpl(size, constraints, buttonHeight, outerPadding)


        return MeasureResult(width, height)
    }

    private fun buttonHeightImpl(
        size: SizeModifier,
        constraints: Constraints,
        buttonHeight: Float,
        outerPadding: PaddingValues
    ): Float = when (val value = size.height) {

        is SizeValue.Fixed ->
            value.dp.toPx()

        SizeValue.FillMax ->
            constraints.maxHeight

        is SizeValue.FillFraction ->
            constraints.maxHeight * value.fraction

        SizeValue.WrapContent, null ->
            buttonHeight + outerPadding.top.toPx() + outerPadding.bottom.toPx()
    }

    private fun buttonWidthImpl(
        size: SizeModifier,
        constraints: Constraints,
        buttonWidth: Float,
        outerPadding: PaddingValues
    ): Float = when (val value = size.width) {

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


    override fun layout() {

        val child = textNode
        val outerPadding = modifier.resolvePadding()

        val paddingStartPx = outerPadding.start.toPx()
        val paddingTopPx = outerPadding.top.toPx()
        val paddingEndPx = outerPadding.end.toPx()
        val paddingBottomPx = outerPadding.bottom.toPx()

        buttonX = x + paddingStartPx
        buttonY = y + paddingTopPx

        buttonWidth =
            width -
                    paddingStartPx -
                    paddingEndPx

        buttonHeight =
            height -
                    paddingTopPx -
                    paddingBottomPx

        centerX =
            buttonX +
                    buttonWidth / 2f

        centerY =
            buttonY +
                    buttonHeight / 2f

        cornerRadiusPx =
            cornerRadius.toPx()

        elevationPx =
            elevation.toPx()
        borderWidthPx =
            borderWidth.toPx()

        shadowOffsetYPx =
            elevationPx * 0.75f

        shadowBlurPx =
            elevationPx * 2f

        shadowAlpha =
            (elevationPx * 8f)
                .coerceAtMost(60f)
                .toInt()



        contentAlignmentImpl(
            child = child,
            buttonX = buttonX,
            buttonWidth = buttonWidth
        )

        child.y =
            buttonY +
                    (buttonHeight - child.height) / 2f

        child.layout()

        invalidateGradientCache()
    }

    private fun invalidateGradientCache() {
        cachedGradient = null
        cachedGradientWidth = 0f
        cachedGradientHeight = 0f
        cachedGradientShader = null
    }

    private fun contentAlignmentImpl(
        child: TextNode,
        buttonX: Float,
        buttonWidth: Float
    ) {
        when (contentAlignment) {

            Alignment.Start -> {
                child.x = buttonX + contentPadding.start.toPx()
            }

            Alignment.Center -> {
                child.x = buttonX + (buttonWidth - child.width) / 2f
            }

            Alignment.End -> {
                child.x = buttonX + buttonWidth - child.width - contentPadding.end.toPx()
            }
        }
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

    override fun draw(
        canvas: Canvas
    ) {

        canvas.save()

        canvas.scale(
            currentScale,
            currentScale,
            centerX,
            centerY
        )

        drawElevation(canvas)

        setupBackgroundPaint()

        backgroundPaint.alpha =
            (255f * currentAlpha)
                .toInt()
                .coerceIn(0, 255)

        canvas.drawRoundRect(
            buttonX,
            buttonY,
            buttonX + buttonWidth,
            buttonY + buttonHeight,
            cornerRadiusPx,
            cornerRadiusPx,
            backgroundPaint
        )


        textNode.draw(canvas)

        canvas.restore()
    }


    private fun setupBackgroundPaint() {

        when (style) {

            ButtonStyle.Filled -> {

                backgroundPaint.style =
                    Paint.Style.FILL

                if (enabled && gradient != null) {

                    backgroundPaint.shader =
                        getGradientShader()

                } else {

                    backgroundPaint.shader = null

                    backgroundPaint.color =
                        if (enabled) {
                            backgroundColor
                        } else {
                            disabledBackgroundColor
                        }
                }
            }

            ButtonStyle.Outlined -> {

                backgroundPaint.shader = null

                backgroundPaint.style =
                    Paint.Style.STROKE

                backgroundPaint.strokeWidth =
                    borderWidthPx

                backgroundPaint.color =
                    if (enabled) {
                        borderColor
                    } else {
                        disabledBorderColor
                    }
            }
        }
    }

    private fun drawElevation(
        canvas: Canvas
    ) {

        if (elevationPx <= 0f) {
            return
        }

        shadowPaint.color =
            Color.argb(
                shadowAlpha,
                0,
                0,
                0
            )

        shadowPaint.setShadowLayer(
            shadowBlurPx,
            0f,
            shadowOffsetYPx,
            shadowPaint.color
        )

        canvas.drawRoundRect(
            buttonX,
            buttonY,
            buttonX + buttonWidth,
            buttonY + buttonHeight,
            cornerRadiusPx,
            cornerRadiusPx,
            shadowPaint
        )

        shadowPaint.clearShadowLayer()
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