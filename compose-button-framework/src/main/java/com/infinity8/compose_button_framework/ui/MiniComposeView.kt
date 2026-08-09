package com.infinity8.compose_button_framework.ui

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import com.infinity8.compose_button_framework.node.RootNode
import com.infinity8.compose_button_framework.render.Renderer

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
    init {

        renderer.setInvalidateCallback {
            postInvalidateOnAnimation()
        }
    }
    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(
        event: MotionEvent
    ): Boolean {

        return when (event.actionMasked) {

            MotionEvent.ACTION_DOWN -> {
                renderer.onTouchDown(
                    event.x,
                    event.y
                )
            }

            MotionEvent.ACTION_UP -> {
                renderer.onTouchUp(
                    event.x,
                    event.y
                )
            }

            MotionEvent.ACTION_CANCEL -> {
                renderer.onTouchCancel()
            }

            else -> {
                true
            }
        }
    }

    fun setRoot(root: RootNode) {

        renderer.setRoot(root)

        requestLayout()
        postInvalidateOnAnimation()
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