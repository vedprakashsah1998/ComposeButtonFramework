package com.infinity8.mini_compose.node

import android.graphics.Canvas
import android.graphics.Paint
import androidx.compose.ui.unit.Dp
import com.infinity8.mini_compose.layout.Constraints
import com.infinity8.mini_compose.layout.MeasureResult
import com.infinity8.mini_compose.ui.unit.toPx

class TextNode(
    var text: String,
    var textColor: Int,
    val textSize: Dp
) : LayoutNode() {
    var alpha = 1f
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        textAlign = Paint.Align.CENTER
    }
    override fun measure(
        constraints: Constraints
    ): MeasureResult {

        paint.textSize = textSize.toPx()

        val textWidth = paint.measureText(text)

        val textHeight =
            paint.descent() - paint.ascent()

        width = textWidth
        height = textHeight

        return MeasureResult(
            width,
            height
        )
    }

    override fun layout() {
        // Parent decides position
    }

    override fun draw(canvas: Canvas) {

        paint.color = textColor
        paint.alpha = (255 * alpha).toInt()

        paint.textSize = textSize.toPx()

        val centerY =
            y + (height / 2f) -
                    ((paint.descent() + paint.ascent()) / 2f)

        canvas.drawText(
            text,
            x + width / 2f,
            centerY,
            paint
        )
    }
}