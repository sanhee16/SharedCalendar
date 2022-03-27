package com.example.sharedcalendar

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.text.TextPaint
import android.util.AttributeSet
import android.view.ContextThemeWrapper
import android.view.View
import androidx.annotation.AttrRes
import androidx.annotation.StyleRes
import androidx.core.content.ContextCompat
import androidx.core.content.withStyledAttributes

@SuppressLint("ViewConstructor")
class DayView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    @AttrRes private val defStyleAttr: Int = R.attr.itemViewStyle,
    @StyleRes private val defStyleRes: Int = R.style.Calendar_ItemViewStyle,
    private var item: DayItem
) : View(ContextThemeWrapper(context, defStyleRes), attrs, defStyleAttr) {

    private val bounds = Rect()
    private var paint: Paint = Paint()

    init {
        /* Attributes */
        context.withStyledAttributes(attrs, R.styleable.CalendarView, defStyleAttr, defStyleRes) {
            val dayTextSize =
                getDimensionPixelSize(R.styleable.CalendarView_dayTextSize, 0).toFloat()
            paint = TextPaint().apply {
                color = ContextCompat.getColor(
                    context, when (item.date.dayOfWeek.value) {
                        6 -> R.color.saturday
                        7 -> R.color.sunday
                        else -> R.color.weekday
                    }
                )
                isAntiAlias = true
                textSize = dayTextSize
                if (!item.isCurrentMonth) alpha = 50
            }
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if (canvas == null) return

        val date = item.date.dayOfMonth.toString()
        paint.getTextBounds(date, 0, date.length, bounds)
        canvas.drawText(
            date,
            (width / 10).toFloat(),
            (height / 4).toFloat(),
            paint
        )
    }
}