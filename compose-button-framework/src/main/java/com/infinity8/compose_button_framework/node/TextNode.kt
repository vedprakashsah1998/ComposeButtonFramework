package com.infinity8.compose_button_framework.node

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Typeface
import android.text.Layout
import android.text.StaticLayout
import android.text.TextPaint
import androidx.compose.ui.unit.Dp
import com.infinity8.compose_button_framework.FontFamily
import com.infinity8.compose_button_framework.FontWeight
import com.infinity8.compose_button_framework.TextOverflow
import com.infinity8.compose_button_framework.layout.Constraints
import com.infinity8.compose_button_framework.layout.MeasureResult
import com.infinity8.compose_button_framework.resource.FontFamilyResolver
import com.infinity8.compose_button_framework.resource.FontStyle
import com.infinity8.compose_button_framework.ui.unit.toPx

class TextNode(
    var text: String,
    var textColor: Int,
    var textSize: Dp,
    var fontFamily: FontFamily = FontFamily.Default,
    var fontWeight: FontWeight = FontWeight.Normal,
    private val fontResolver: FontFamilyResolver? = null,
    var maxLines: Int = Int.MAX_VALUE,
    var overflow: TextOverflow = TextOverflow.Clip,
    var isUpperCase: Boolean = false,
    var fontStyle: FontStyle = FontStyle.Normal,
    var textAlignment: Alignment = Alignment.Center,
) : LayoutNode() {
    private var staticLayout: StaticLayout? = null
    private var lastAlignment: Alignment? = null
    private var lastLayoutText = ""
    private var lastLayoutWidth = -1
    var alpha = 1f
    var availableWidth = Float.MAX_VALUE
    private var lastAvailableWidth = Float.MAX_VALUE
    private val paint = TextPaint(Paint.ANTI_ALIAS_FLAG).apply {
        textAlign = Paint.Align.LEFT
    }

    private fun getTransformedText(): String =
        if (isUpperCase) text.uppercase() else text

    private var lastMaxLines = maxLines

    private fun invalidateLayout() {
        staticLayout = null
    }

    private fun buildDisplayText(): String {
        val transformedText = getTransformedText()

        // Apply overflow on transformed text

        if (paint.measureText(transformedText) <= availableWidth) {
            return transformedText
        }

        return when (overflow) {
            TextOverflow.Visible ->
                transformedText

            TextOverflow.Clip ->
                clipText(transformedText)

            TextOverflow.Ellipsis ->
                endEllipsis(transformedText)

            TextOverflow.StartEllipsis ->
                startEllipsis(transformedText)

            TextOverflow.MiddleEllipsis ->
                middleEllipsis(transformedText)
        }
    }

    private fun clipText(text: String): String {

        var end = text.length

        while (end > 0 &&
            paint.measureText(text.substring(0, end)) > availableWidth
        ) {
            end--
        }

        return text.substring(0, end)
    }

    private fun endEllipsis(text: String): String {

        val ellipsis = "..."

        if (paint.measureText(ellipsis) > availableWidth) {
            return ""
        }

        var end = text.length

        while (end > 0 &&
            paint.measureText(text.substring(0, end) + ellipsis) > availableWidth
        ) {
            end--
        }

        return text.substring(0, end) + ellipsis
    }

    private fun startEllipsis(text: String): String {

        val ellipsis = "..."

        if (paint.measureText(ellipsis) > availableWidth) {
            return ""
        }

        var start = 0

        while (start < text.length &&
            paint.measureText(ellipsis + text.substring(start)) > availableWidth
        ) {
            start++
        }

        return ellipsis + text.substring(start)
    }

    private fun middleEllipsis(text: String): String {

        val ellipsis = "..."

        if (paint.measureText(ellipsis) > availableWidth) {
            return ""
        }

        var left = text.length / 2
        var right = left

        while (left >= 0 && right <= text.length) {

            val candidate =
                text.substring(0, left) +
                        ellipsis +
                        text.substring(right)

            if (paint.measureText(candidate) <= availableWidth) {
                return candidate
            }

            if (left > 0) {
                left--
            }

            if (right < text.length) {
                right++
            }
        }

        return ellipsis
    }

    override fun measure(constraints: Constraints): MeasureResult {

        updatePaint()

        val displayText = buildDisplayText()
        if (lastAlignment != textAlignment) {
            invalidateLayout()
        }
        if (useStaticLayout() && lastLayoutText != displayText) {
            invalidateLayout()
        }

        if (lastMaxLines != maxLines) {
            lastMaxLines = maxLines
            invalidateLayout()
        }

        if (lastAvailableWidth != availableWidth) {
            lastAvailableWidth = availableWidth
            invalidateLayout()
        }

        if (
            useStaticLayout()
        ) {

            ensureStaticLayout(displayText)

            width =
                if (overflow == TextOverflow.Visible) {
                    paint.measureText(displayText)
                } else {
                    staticLayout!!.width.toFloat()
                }
            height = staticLayout!!.height.toFloat()

        } else {
            width = paint.measureText(displayText)

            height = paint.descent() - paint.ascent()
        }

        return MeasureResult(width, height)
    }

    override fun layout() {
        // Parent decides position
    }

    private fun resolveLayoutAlignment(): Layout.Alignment =
        when (textAlignment) {
            Alignment.Start -> Layout.Alignment.ALIGN_NORMAL
            Alignment.Center -> Layout.Alignment.ALIGN_CENTER
            Alignment.End -> Layout.Alignment.ALIGN_OPPOSITE
        }

    private fun updatePaint() {

        val size = textSize.toPx()

        if (paint.textSize != size) {
            paint.textSize = size
            invalidateLayout()
        }

        val tf =
            fontResolver?.resolve(
                fontFamily,
                fontWeight,
                fontStyle
            ) ?: Typeface.DEFAULT

        if (paint.typeface != tf) {
            paint.typeface = tf
            invalidateLayout()
        }
    }

    private fun ensureStaticLayout(displayText: String) {

        val layoutWidth =
            if (overflow == TextOverflow.Visible) {
                kotlin.math.ceil(
                    Layout.getDesiredWidth(displayText, paint).toDouble()
                ).toInt().coerceAtLeast(1)
            } else {
                availableWidth.toInt().coerceAtLeast(1)
            }

        if (
            staticLayout != null &&
            lastLayoutText == displayText &&
            lastLayoutWidth == layoutWidth &&
            lastMaxLines == maxLines &&
            lastAlignment == textAlignment
        ) {
            return
        }

        val builder = StaticLayout.Builder.obtain(
            displayText,
            0,
            displayText.length,
            paint,
            layoutWidth
        )
            .setAlignment(resolveLayoutAlignment())
            .setIncludePad(false)
            .setMaxLines(maxLines)

        when (overflow) {

            TextOverflow.Clip ->
                builder.setEllipsize(null)

            TextOverflow.Ellipsis ->
                builder.setEllipsize(android.text.TextUtils.TruncateAt.END)

            else -> {
                // StartEllipsis and MiddleEllipsis are handled manually.
            }
        }

        staticLayout = builder.build()

        lastLayoutText = displayText
        lastLayoutWidth = layoutWidth
        lastMaxLines = maxLines
        lastAlignment = textAlignment
    }

    override fun draw(canvas: Canvas) {

        updatePaint()

        paint.color = textColor
        paint.alpha =
            (255 * alpha)
                .toInt()
                .coerceIn(0, 255)

        val displayText = buildDisplayText()

        if (
            useStaticLayout()

        ) {

            ensureStaticLayout(displayText)

            canvas.save()

            canvas.translate(
                x,
                y + (height - staticLayout!!.height) / 2f
            )

            staticLayout!!.draw(canvas)

            canvas.restore()

            return
        }

        val centerY =
            y + height / 2f -
                    (paint.descent() + paint.ascent()) / 2f

        val drawX = when (textAlignment) {
            Alignment.Start -> x
            Alignment.Center -> x + (width - paint.measureText(displayText)) / 2f
            Alignment.End -> x + width - paint.measureText(displayText)
        }
        canvas.drawText(
            displayText,
            drawX,
            centerY,
            paint
        )
    }

    private fun useStaticLayout(): Boolean = overflow == TextOverflow.Clip ||
            overflow == TextOverflow.Ellipsis ||
            overflow == TextOverflow.Visible
}