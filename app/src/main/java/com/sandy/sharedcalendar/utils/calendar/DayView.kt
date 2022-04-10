package com.sandy.sharedcalendar.utils.calendar

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.RectF
import android.text.TextPaint
import android.util.AttributeSet
import android.view.ContextThemeWrapper
import android.view.View
import androidx.annotation.AttrRes
import androidx.annotation.StyleRes
import androidx.core.content.ContextCompat
import androidx.core.content.withStyledAttributes
import com.sandy.sharedcalendar.R
import com.sandy.sharedcalendar.data.DayItem

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
    private var isCurrentMonth = false

    init {
        isCurrentMonth = item.isCurrentMonth
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
                if (!item.isCurrentMonth) alpha = 30
            }
        }
    }

    @SuppressLint("DrawAllocation")
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
        val item = RectF()
        val itemWidthLeft = width / 20
        val itemWidth = width / 5
        val itemHeightTop = height / 3
        val itemHeight = height / 10
        val itemWidthPadding = width / 16
        val itemHeightPadding = height / 40

        val colorList = listOf(
            R.color.schedule_item_1,
            R.color.schedule_item_2,
            R.color.schedule_item_3,
            R.color.schedule_item_4,
            R.color.schedule_item_5,
            R.color.schedule_item_6
        )

        for (i in 0..5) {
            val itemPaint = Paint().apply {
                color = ContextCompat.getColor(context, colorList[i])
                if (!isCurrentMonth) alpha = 30
            }
            var widthStart = 0
            var heightStart = itemHeightTop
            var widthEnd = 0
            var heightEnd = heightStart + itemHeight
            if (i < 3) {
                widthStart = itemWidthLeft + (itemWidth + itemWidthPadding) * i
                widthEnd = widthStart + itemWidth
            } else {
                widthStart = itemWidthLeft + (itemWidth + itemWidthPadding) * (i - 3)
                heightStart += itemHeightPadding + itemHeight
                widthEnd = widthStart + itemWidth
                heightEnd = heightStart + itemHeight
            }

            item.set(
                widthStart.toFloat(),
                heightStart.toFloat(),
                widthEnd.toFloat(),
                heightEnd.toFloat()
            )
            canvas.drawRoundRect(item, 10f, 10f, itemPaint)
        }

    }
}