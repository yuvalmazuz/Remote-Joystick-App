package com.example.remotejoystick.views


import android.graphics.PointF
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import kotlin.math.abs
import kotlin.math.min
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint

class Joystick @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    private val mainCircle = Paint().apply {
        color = Color.BLACK
        style = Paint.Style.FILL_AND_STROKE
        isAntiAlias = true
    }
    private val innerCircle = Paint().apply {
        color = Color.GRAY
        style = Paint.Style.FILL_AND_STROKE
        isAntiAlias = true
    }
    private var innerR: Float = 0f
    private var innerC: PointF = PointF()
    private var outerR: Float = 0f
    private var outerC: PointF = PointF()
    var aileron: Float = 0f
    var elevator: Float = 0f
    lateinit var onChange: (Float, Float) -> Unit
    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        innerR = 0.1f * min(w, h)
        innerC = PointF(width / 2.0f, height / 2.0f)
        outerR = 0.3f * min(w, h)
        outerC = PointF(width / 2.0f, height / 2.0f)
    }

    override fun onDraw(canvas: Canvas) {
        canvas.drawCircle(outerC.x, outerC.y, outerR, mainCircle)
        canvas.drawCircle(innerC.x, innerC.y, innerR, innerCircle)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when (event?.action) {
            MotionEvent.ACTION_MOVE -> touchMove(event.x, event.y)
            MotionEvent.ACTION_UP -> touchUp(event.x, event.y)
        }
        return true
    }

    private fun touchMove(x: Float, y: Float) {
        val distanceX = abs(x - outerC.x)
        val distanceY = abs(y - outerC.y)
        if ((outerR >= distanceX) && (outerR >= distanceY)) {
            innerC.x = x;
            innerC.y = y;

            aileron = (x - outerC.x) / (outerR)
            elevator = (y - outerC.y) / (outerR)

            try{
                onChange(aileron,elevator)
            } catch (e : Exception){
                e.printStackTrace()
            }
        }
        invalidate()
    }

    private fun touchUp(x: Float, y: Float) {
        innerC.x = outerC.x;
        innerC.y = outerC.y;
        invalidate()
    }
}