package com.hiconsy.app.hds.kit.switch
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.core.content.ContextCompat
import com.hiconsy.app.hds.R

class LabeledSwitch @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attributeSet, defStyleAttr) {
    private var padding = 0
    private var textSize = 0
    private var outerRadii = 0
    private var thumbRadii = 0
    private var paint: Paint? = null
    private var startTime: Long = 0
    private var labelOn: String? = null
    private var labelOff: String? = null
    private var thumbBounds: RectF? = null
    private var leftBgArc: RectF? = null
    private var rightBgArc: RectF? = null
    private var leftFgArc: RectF? = null
    private var rightFgArc: RectF? = null
    private var toggleWidth = 0
    private var toggleHeight = 0
    private var isOn = false
    fun isChecked() = isOn

    private var toggleEnabled: Boolean = true
        set(value) {
            field = value
        }

    private var onToggledListener: OnToggledListener? = null
    var typeface: Typeface? = null
        set(typeface) {
            field = typeface
            paint!!.typeface = typeface
            invalidate()
        }
    private var thumbOnCenterX = 0f
    private var thumbOffCenterX = 0f

    init {
        initView()
        initProperties(attributeSet)
    }
    fun setOnToggleListener(listener: OnToggledListener) {
        this.onToggledListener = listener
    }

    private fun initView() {
        isOn = false
        labelOn = "ON"
        labelOff = "OFF"
        toggleEnabled = true
        textSize = (12f * resources.displayMetrics.scaledDensity).toInt()
        paint = Paint()
        paint!!.isAntiAlias = true
        leftBgArc = RectF()
        rightBgArc = RectF()
        leftFgArc = RectF()
        rightFgArc = RectF()
        thumbBounds = RectF()
    }

    private fun initProperties(attrs: AttributeSet?) {
        val tarr = context.theme.obtainStyledAttributes(attrs, R.styleable.LabeledSwitch, 0, 0)
        val N = tarr.indexCount
        for (i in 0 until N) {
            val attr = tarr.getIndex(i)
            if (attr == R.styleable.LabeledSwitch_on) {
                isOn = tarr.getBoolean(R.styleable.LabeledSwitch_on, false)
            } else if (attr == R.styleable.LabeledSwitch_textOff) {
                labelOff = tarr.getString(R.styleable.LabeledSwitch_textOff)
            } else if (attr == R.styleable.LabeledSwitch_textOn) {
                labelOn = tarr.getString(R.styleable.LabeledSwitch_textOn)
            } else if (attr == R.styleable.LabeledSwitch_android_textSize) {
                val defaultTextSize = (12f * resources.displayMetrics.scaledDensity).toInt()
                textSize =
                    tarr.getDimensionPixelSize(R.styleable.LabeledSwitch_android_textSize, defaultTextSize)
            } else if (attr == R.styleable.LabeledSwitch_android_enabled) {
                isEnabled = tarr.getBoolean(R.styleable.LabeledSwitch_android_enabled, false)
            }
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        paint!!.textSize = textSize.toFloat()

//      Drawing Switch background here
        run {
            /* 테두리 색상 */
            if (isEnabled && isOn) {
                paint!!.color = ContextCompat.getColor(context, R.color.black)
            } else if (isEnabled && !isOn) {
                paint!!.color = ContextCompat.getColor(context, R.color.gray_200)
            } else if (!isEnabled && isOn) {
                paint!!.color = ContextCompat.getColor(context, R.color.gray_400)
            } else {
                paint!!.color = ContextCompat.getColor(context, R.color.gray_400)
            }
            canvas.drawArc(leftBgArc!!, 90f, 180f, false, paint!!)
            canvas.drawArc(rightBgArc!!, 90f, -180f, false, paint!!)
            canvas.drawRect(
                outerRadii.toFloat(),
                0f,
                (toggleWidth - outerRadii).toFloat(),
                toggleHeight.toFloat(),
                paint!!
            )

            canvas.drawArc(leftFgArc!!, 90f, 180f, false, paint!!)
            canvas.drawArc(rightFgArc!!, 90f, -180f, false, paint!!)
            canvas.drawRect(
                outerRadii.toFloat(),
                (padding / 10).toFloat(),
                (toggleWidth - outerRadii).toFloat(),
                (toggleHeight - padding / 10).toFloat(),
                paint!!
            )
            var alpha =
                ((thumbBounds!!.centerX() - thumbOffCenterX) / (thumbOnCenterX - thumbOffCenterX) * 255).toInt()
            alpha = if (alpha < 0) 0 else if (alpha > 255) 255 else alpha

            if (isEnabled && isOn) {
                paint!!.color = ContextCompat.getColor(context, R.color.black)
            } else if (isEnabled && !isOn) {
                paint!!.color = ContextCompat.getColor(context, R.color.gray_200)
            } else if (!isEnabled && isOn) {
                paint!!.color = ContextCompat.getColor(context, R.color.gray_400)
            } else {
                paint!!.color = ContextCompat.getColor(context, R.color.gray_400)
            }
            canvas.drawArc(leftBgArc!!, 90f, 180f, false, paint!!)
            canvas.drawArc(rightBgArc!!, 90f, -180f, false, paint!!)
            canvas.drawRect(
                outerRadii.toFloat(),
                0f,
                (toggleWidth - outerRadii).toFloat(),
                toggleHeight.toFloat(),
                paint!!
            )
            alpha =
                ((thumbOnCenterX - thumbBounds!!.centerX()) / (thumbOnCenterX - thumbOffCenterX) * 255).toInt()
            alpha = if (alpha < 0) 0 else if (alpha > 255) 255 else alpha
            canvas.drawArc(leftFgArc!!, 90f, 180f, false, paint!!)
            canvas.drawArc(rightFgArc!!, 90f, -180f, false, paint!!)
            canvas.drawRect(
                outerRadii.toFloat(),
                (padding / 10).toFloat(),
                (toggleWidth - outerRadii).toFloat(),
                (toggleHeight - padding / 10).toFloat(),
                paint!!
            )
        }

//      라벨 색상
        val MAX_CHAR = "N"
        val textCenter = paint!!.measureText(MAX_CHAR) / 2
        if (isEnabled && isOn) {
            paint!!.color = ContextCompat.getColor(context, R.color.black)
            var alpha =
                (((toggleWidth ushr 1) - thumbBounds!!.centerX()) / ((toggleWidth ushr 1) - thumbOffCenterX) * 255).toInt()
            alpha = if (alpha < 0) 0 else if (alpha > 255) 255 else alpha
            var centerX =
                (toggleWidth - padding - (padding + (padding ushr 1) + (thumbRadii shl 1)) ushr 1).toFloat()
            canvas.drawText(
                labelOff!!,
                padding + (padding ushr 1) + (thumbRadii shl 1) + centerX - paint!!.measureText(
                    labelOff
                ) / 2,
                (toggleHeight ushr 1) + textCenter,
                paint!!
            )

            paint!!.color = ContextCompat.getColor(context, R.color.white)
            alpha =
                ((thumbBounds!!.centerX() - (toggleWidth ushr 1)) / (thumbOnCenterX - (toggleWidth ushr 1)) * 255).toInt()
            alpha = if (alpha < 0) 0 else if (alpha > 255) 255 else alpha
            val maxSize = toggleWidth - (padding shl 1) - (thumbRadii shl 1)
            centerX = ((padding ushr 1) + maxSize - padding ushr 1).toFloat()
            canvas.drawText(
                labelOn!!,
                padding + centerX - paint!!.measureText(labelOn) / 2,
                (toggleHeight ushr 1) + textCenter,
                paint!!
            )
        } else if (isEnabled && !isOn) {
            paint!!.color = ContextCompat.getColor(context, R.color.white)
            var alpha =
                (((toggleWidth ushr 1) - thumbBounds!!.centerX()) / ((toggleWidth ushr 1) - thumbOffCenterX) * 255).toInt()
            alpha = if (alpha < 0) 0 else if (alpha > 255) 255 else alpha
            var centerX =
                (toggleWidth - padding - (padding + (padding ushr 1) + (thumbRadii shl 1)) ushr 1).toFloat()
            canvas.drawText(
                labelOff!!,
                padding + (padding ushr 1) + (thumbRadii shl 1) + centerX - paint!!.measureText(
                    labelOff
                ) / 2,
                (toggleHeight ushr 1) + textCenter,
                paint!!
            )

            paint!!.color = ContextCompat.getColor(context, R.color.gray_200)
            alpha =
                ((thumbBounds!!.centerX() - (toggleWidth ushr 1)) / (thumbOnCenterX - (toggleWidth ushr 1)) * 255).toInt()
            alpha = if (alpha < 0) 0 else if (alpha > 255) 255 else alpha
            val maxSize = toggleWidth - (padding shl 1) - (thumbRadii shl 1)
            centerX = ((padding ushr 1) + maxSize - padding ushr 1).toFloat()
            canvas.drawText(
                labelOn!!,
                padding + centerX - paint!!.measureText(labelOn) / 2,
                (toggleHeight ushr 1) + textCenter,
                paint!!
            )
            paint!!.color = ContextCompat.getColor(context, R.color.gray_200)
        } else if (!isEnabled && isOn) {
            paint!!.color = ContextCompat.getColor(context, R.color.gray_400)
            var alpha =
                (((toggleWidth ushr 1) - thumbBounds!!.centerX()) / ((toggleWidth ushr 1) - thumbOffCenterX) * 255).toInt()
            alpha = if (alpha < 0) 0 else if (alpha > 255) 255 else alpha
            var centerX =
                (toggleWidth - padding - (padding + (padding ushr 1) + (thumbRadii shl 1)) ushr 1).toFloat()
            canvas.drawText(
                labelOff!!,
                padding + (padding ushr 1) + (thumbRadii shl 1) + centerX - paint!!.measureText(
                    labelOff
                ) / 2,
                (toggleHeight ushr 1) + textCenter,
                paint!!
            )

            paint!!.color = ContextCompat.getColor(context, R.color.gray_300)
            alpha =
                ((thumbBounds!!.centerX() - (toggleWidth ushr 1)) / (thumbOnCenterX - (toggleWidth ushr 1)) * 255).toInt()
            alpha = if (alpha < 0) 0 else if (alpha > 255) 255 else alpha
            val maxSize = toggleWidth - (padding shl 1) - (thumbRadii shl 1)
            centerX = ((padding ushr 1) + maxSize - padding ushr 1).toFloat()
            canvas.drawText(
                labelOn!!,
                padding + centerX - paint!!.measureText(labelOn) / 2,
                (toggleHeight ushr 1) + textCenter,
                paint!!
            )
        } else {
            paint!!.color = ContextCompat.getColor(context, R.color.gray_300)
            var alpha =
                (((toggleWidth ushr 1) - thumbBounds!!.centerX()) / ((toggleWidth ushr 1) - thumbOffCenterX) * 255).toInt()
            alpha = if (alpha < 0) 0 else if (alpha > 255) 255 else alpha
            var centerX =
                (toggleWidth - padding - (padding + (padding ushr 1) + (thumbRadii shl 1)) ushr 1).toFloat()
            canvas.drawText(
                labelOff!!,
                padding + (padding ushr 1) + (thumbRadii shl 1) + centerX - paint!!.measureText(
                    labelOff
                ) / 2,
                (toggleHeight ushr 1) + textCenter,
                paint!!
            )

            paint!!.color = ContextCompat.getColor(context, R.color.gray_400)
            alpha =
                ((thumbBounds!!.centerX() - (toggleWidth ushr 1)) / (thumbOnCenterX - (toggleWidth ushr 1)) * 255).toInt()
            alpha = if (alpha < 0) 0 else if (alpha > 255) 255 else alpha
            val maxSize = toggleWidth - (padding shl 1) - (thumbRadii shl 1)
            centerX = ((padding ushr 1) + maxSize - padding ushr 1).toFloat()
            canvas.drawText(
                labelOn!!,
                padding + centerX - paint!!.measureText(labelOn) / 2,
                (toggleHeight ushr 1) + textCenter,
                paint!!
            )
        }

//      Thumb 색상
        run {
            var alpha =
                ((thumbBounds!!.centerX() - thumbOffCenterX) / (thumbOnCenterX - thumbOffCenterX) * 255).toInt()
            alpha = if (alpha < 0) 0 else if (alpha > 255) 255 else alpha
            if (isEnabled && isOn) {
                paint!!.color = ContextCompat.getColor(context, R.color.white)
            } else if (isEnabled && !isOn) {
                paint!!.color = ContextCompat.getColor(context, R.color.white)
            } else if (!isEnabled && isOn) {
                paint!!.color = ContextCompat.getColor(context, R.color.gray_300)
            } else {
                paint!!.color = ContextCompat.getColor(context, R.color.gray_300)
            }
            canvas.drawCircle(
                thumbBounds!!.centerX(),
                thumbBounds!!.centerY(),
                thumbRadii.toFloat(),
                paint!!
            )
            alpha =
                ((thumbOnCenterX - thumbBounds!!.centerX()) / (thumbOnCenterX - thumbOffCenterX) * 255).toInt()
            alpha = if (alpha < 0) 0 else if (alpha > 255) 255 else alpha

            canvas.drawCircle(
                thumbBounds!!.centerX(),
                thumbBounds!!.centerY(),
                thumbRadii.toFloat(),
                paint!!
            )
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val desiredWidth = resources.getDimensionPixelSize(R.dimen.dp_52)
        val desiredHeight = resources.getDimensionPixelSize(R.dimen.dp_24)
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)
        if (widthMode == MeasureSpec.EXACTLY) {
            toggleWidth = widthSize
        } else if (widthMode == MeasureSpec.AT_MOST) {
            toggleWidth = Math.min(desiredWidth, widthSize)
        } else {
            toggleWidth = desiredWidth
        }
        if (heightMode == MeasureSpec.EXACTLY) {
            toggleHeight = heightSize
        } else if (heightMode == MeasureSpec.AT_MOST) {
            toggleHeight = Math.min(desiredHeight, heightSize)
        } else {
            toggleHeight = desiredHeight
        }
        setMeasuredDimension(toggleWidth, toggleHeight)
        outerRadii = Math.min(toggleWidth, toggleHeight) ushr 1
        thumbRadii = (Math.min(toggleWidth, toggleHeight) / 2.5f).toInt()
        padding = toggleHeight - thumbRadii ushr 1
        thumbBounds!![(toggleWidth - padding - thumbRadii).toFloat(), padding.toFloat(), (toggleWidth - padding).toFloat()] =
            (toggleHeight - padding).toFloat()
        thumbOnCenterX = thumbBounds!!.centerX()
        thumbBounds!![padding.toFloat(), padding.toFloat(), (padding + thumbRadii).toFloat()] =
            (toggleHeight - padding).toFloat()
        thumbOffCenterX = thumbBounds!!.centerX()
        if (isOn) {
            thumbBounds!![(toggleWidth - padding - thumbRadii).toFloat(), padding.toFloat(), (toggleWidth - padding).toFloat()] =
                (toggleHeight - padding).toFloat()
        } else {
            thumbBounds!![padding.toFloat(), padding.toFloat(), (padding + thumbRadii).toFloat()] =
                (toggleHeight - padding).toFloat()
        }
        leftBgArc!![0f, 0f, (outerRadii shl 1).toFloat()] = toggleHeight.toFloat()
        rightBgArc!![(toggleWidth - (outerRadii shl 1)).toFloat(), 0f, toggleWidth.toFloat()] =
            toggleHeight.toFloat()
        leftFgArc!![(padding / 10).toFloat(), (padding / 10).toFloat(), ((outerRadii shl 1) - padding / 10).toFloat()] =
            (toggleHeight - padding / 10).toFloat()
        rightFgArc!![(toggleWidth - (outerRadii shl 1) + padding / 10).toFloat(), (padding / 10).toFloat(), (toggleWidth - padding / 10).toFloat()] =
            (toggleHeight - padding / 10).toFloat()
    }

    override fun performClick(): Boolean {
        super.performClick()
        if (isOn) {
            val switchColor = ValueAnimator.ofFloat(
                (toggleWidth - padding - thumbRadii).toFloat(),
                padding.toFloat()
            )
            switchColor.addUpdateListener { animation: ValueAnimator ->
                val value = animation.animatedValue as Float
                thumbBounds!![value, thumbBounds!!.top, value + thumbRadii] = thumbBounds!!.bottom
                invalidate()
            }
            switchColor.interpolator = AccelerateDecelerateInterpolator()
            switchColor.duration = 250
            switchColor.start()
        } else {
            val switchColor = ValueAnimator.ofFloat(
                padding.toFloat(),
                (toggleWidth - padding - thumbRadii).toFloat()
            )
            switchColor.addUpdateListener { animation: ValueAnimator ->
                val value = animation.animatedValue as Float
                thumbBounds!![value, thumbBounds!!.top, value + thumbRadii] = thumbBounds!!.bottom
                invalidate()
            }
            switchColor.interpolator = AccelerateDecelerateInterpolator()
            switchColor.duration = 250
            switchColor.start()
        }
        isOn = !isOn
        if (onToggledListener != null) {
            onToggledListener!!.onSwitched(this, isOn)
        }
        return true
    }

    /**
     * Method to handle touch screen motion events.
     *
     * @param event The motion event.
     * @return True if the event was handled, false otherwise.
     */
    override fun onTouchEvent(event: MotionEvent): Boolean {
        return if (isEnabled) {
            val x = event.x
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    startTime = System.currentTimeMillis()
                    true
                }

                MotionEvent.ACTION_MOVE -> {
                    if (x - (thumbRadii ushr 1) > padding && x + (thumbRadii ushr 1) < toggleWidth - padding) {
                        thumbBounds!![x - (thumbRadii ushr 1), thumbBounds!!.top, x + (thumbRadii ushr 1)] =
                            thumbBounds!!.bottom
                        invalidate()
                    }
                    true
                }

                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                    val endTime = System.currentTimeMillis()
                    val span = endTime - startTime
                    if (span < 200) {
                        performClick()
                    } else {
                        if (x >= toggleWidth ushr 1) {
                            val switchColor = ValueAnimator.ofFloat(
                                if (x > toggleWidth - padding - thumbRadii) (toggleWidth - padding - thumbRadii).toFloat() else x,
                                (toggleWidth - padding - thumbRadii).toFloat()
                            )
                            switchColor.addUpdateListener { animation: ValueAnimator ->
                                val value =
                                    animation.animatedValue as Float
                                thumbBounds!![value, thumbBounds!!.top, value + thumbRadii] =
                                    thumbBounds!!.bottom
                                invalidate()
                            }
                            switchColor.interpolator = AccelerateDecelerateInterpolator()
                            switchColor.duration = 250
                            switchColor.start()
                            isOn = true
                        } else {
                            val switchColor = ValueAnimator.ofFloat(
                                if (x < padding) padding.toFloat() else x,
                                padding.toFloat()
                            )
                            switchColor.addUpdateListener { animation: ValueAnimator ->
                                val value =
                                    animation.animatedValue as Float
                                thumbBounds!![value, thumbBounds!!.top, value + thumbRadii] =
                                    thumbBounds!!.bottom
                                invalidate()
                            }
                            switchColor.interpolator = AccelerateDecelerateInterpolator()
                            switchColor.duration = 250
                            switchColor.start()
                            isOn = false
                        }
                        if (onToggledListener != null) {
                            onToggledListener!!.onSwitched(this, isOn)
                        }
                    }
                    invalidate()
                    true
                }

                else -> {
                    super.onTouchEvent(event)
                }
            }
        } else {
            false
        }
    }

    fun getLabelOn(): String? {
        return labelOn
    }

    fun setLabelOn(labelOn: String?) {
        this.labelOn = labelOn
        invalidate()
    }

    fun getLabelOff(): String? {
        return labelOff
    }

    fun setLabelOff(labelOff: String?) {
        this.labelOff = labelOff
        invalidate()
    }

    fun settingOn(on: Boolean) {
        isOn = on
        if (isOn) {
            thumbBounds!![(toggleWidth - padding - thumbRadii).toFloat(), padding.toFloat(), (toggleWidth - padding).toFloat()] =
                (toggleHeight - padding).toFloat()
        } else {
            thumbBounds!![padding.toFloat(), padding.toFloat(), (padding + thumbRadii).toFloat()] =
                (toggleHeight - padding).toFloat()
        }
        invalidate()
    }

    fun setTextSize(textSize: Int) {
        this.textSize = (textSize * resources.displayMetrics.scaledDensity).toInt()
        invalidate()
    }
}
