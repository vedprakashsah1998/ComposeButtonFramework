package com.infinity8.mini_compose.ui

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.util.Log
import android.view.View
import com.infinity8.mini_compose.node.RootNode
import com.infinity8.mini_compose.render.Renderer

class MiniComposeView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : View(context, attrs) {
    private val renderer = Renderer()

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        renderer.draw(
            canvas = canvas,
        )
    }


    fun setRoot(root: RootNode) {
        renderer.setRoot(root)

        requestLayout()
        invalidate()
    }

    override fun onMeasure(
        widthMeasureSpec: Int,
        heightMeasureSpec: Int
    ) {
        val availableWidth = MeasureSpec.getSize(widthMeasureSpec)

        renderer.measure(
            width = availableWidth.toFloat(),
            height = Float.MAX_VALUE
        )

        setMeasuredDimension(
            renderer.measuredWidth.toInt(),
            renderer.measuredHeight.toInt()
        )
    }

    override fun onLayout(
        changed: Boolean,
        left: Int,
        top: Int,
        right: Int,
        bottom: Int
    ) {
        super.onLayout(changed, left, top, right, bottom)

        Log.d("MiniCompose", "layout")
    }

}