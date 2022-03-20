package com.example.sharedcalendar

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Rect
import android.os.Build
import android.os.strictmode.CredentialProtectedWhileLockedViolation
import android.text.TextPaint
import android.util.AttributeSet
import android.util.Log
import android.view.ContextThemeWrapper
import android.view.ViewGroup
import androidx.annotation.AttrRes
import androidx.annotation.RequiresApi
import androidx.annotation.StyleRes
import androidx.core.content.ContextCompat
import androidx.core.content.withStyledAttributes
import androidx.core.view.children
import org.threeten.bp.format.DateTimeFormatter
import java.lang.Integer.max

class CalendarView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    @AttrRes defStyleAttr: Int = R.attr.calendarViewStyle,
    @StyleRes defStyleRes: Int = R.style.Calendar_CalendarViewStyle
) : ViewGroup(ContextThemeWrapper(context, defStyleRes), attrs, defStyleAttr) {
    private var _height: Float = 0f
    private lateinit var list: MutableList<CalendarItem>
    private var topSpace = 0f
    private var headerSize = 0f

    init {
        context.withStyledAttributes(attrs, R.styleable.CalendarView, defStyleAttr, defStyleRes) {
            _height = getDimension(R.styleable.CalendarView_dayHeight, 0f)
            topSpace = getDimension(R.styleable.CalendarView_headerHeight, 0f)
            headerSize = getDimension(R.styleable.CalendarView_headerTextSize, 0f)
        }
        this.list = CalendarUtil.list
    }

    fun makeCalendar() {
        this.list.forEach {
            addView(
                DayView(
                    context = context,
                    item = it
                )
            )
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val h = max(
            suggestedMinimumHeight,
            (_height * CalendarUtil.totalWeek).toInt()
        )
        setMeasuredDimension(getDefaultSize(suggestedMinimumWidth, widthMeasureSpec), h)
    }

    override fun onLayout(p0: Boolean, p1: Int, p2: Int, p3: Int, p4: Int) {
        val iWidth = (width / CalendarUtil.DAYS_PER_WEEK).toFloat()
        val iHeight = (height / (CalendarUtil.totalWeek + 1)).toFloat()

        var index = 0
        children.forEach { view ->
            val left = (index % CalendarUtil.DAYS_PER_WEEK) * iWidth
            val top = topSpace + (index / CalendarUtil.DAYS_PER_WEEK) * iHeight
            Log.v("sandy", "top : $top")

            view.layout(left.toInt(), top.toInt(), (left + iWidth).toInt(), (top + iHeight).toInt())

            index++
        }
    }

    override fun dispatchDraw(canvas: Canvas?) {
        super.dispatchDraw(canvas)
        if (canvas == null) return

        val paint = TextPaint().apply {
            color = Color.BLACK
            isAntiAlias = true
            textSize = headerSize
            alpha = 90
        }

        val str = String.format("%d-%02d", CalendarUtil.year, CalendarUtil.month)
        val bounds = Rect()
        paint.getTextBounds(str, 0, str.length, bounds)
        canvas.drawText(
            str,
            ((width - bounds.width()) / 2).toFloat(),
            (topSpace + bounds.height()) / 2,
            paint
        )
    }
}
