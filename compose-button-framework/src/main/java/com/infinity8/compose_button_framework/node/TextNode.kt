package com.infinity8.compose_button_framework.node

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Typeface
import androidx.compose.ui.unit.Dp
import com.infinity8.compose_button_framework.FontFamily
import com.infinity8.compose_button_framework.FontWeight
import com.infinity8.compose_button_framework.TextOverflow
import com.infinity8.compose_button_framework.TextTransform
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
    var textTransform: TextTransform = TextTransform.None,
    var fontStyle: FontStyle = FontStyle.Normal
) : LayoutNode() {

    var alpha = 1f

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        textAlign = Paint.Align.CENTER
    }

    private fun getDisplayText(): String =
        when (textTransform) {
            TextTransform.None -> text
            TextTransform.Uppercase -> text.uppercase()
            TextTransform.Lowercase -> text.lowercase()
        }

    override fun measure(constraints: Constraints): MeasureResult {

        updatePaint()

        val displayText = getDisplayText()

        width = paint.measureText(displayText)
        height = paint.descent() - paint.ascent()

        return MeasureResult(width, height)
    }

    override fun layout() {
        // Parent decides position
    }

    private fun updatePaint() {

        paint.textSize = textSize.toPx()

        paint.typeface =
            fontResolver?.resolve(
                fontFamily,
                fontWeight,
                fontStyle
            )?: Typeface.DEFAULT
    }

    override fun draw(canvas: Canvas) {

        updatePaint()

        paint.color = textColor
        paint.alpha = (255 * alpha).toInt()

        val displayText = getDisplayText()

        val centerY =
            y + height / 2f -
                    (paint.descent() + paint.ascent()) / 2f

        canvas.drawText(
            displayText,
            x + width / 2f,
            centerY,
            paint
        )
    }
}