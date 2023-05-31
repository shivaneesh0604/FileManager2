package com.example.filemanager2

import android.content.Context
import android.text.TextUtils
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class RecyclerViewItemCustomViewGroup : ViewGroup {
    constructor(context: Context) : super(context) {
    }

    constructor(context: Context, attributeSet: AttributeSet?) : super(context, attributeSet) {
    }

    constructor(context: Context, attributeSet: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attributeSet,
        defStyleAttr
    ) {
    }

    override fun onLayout(
        changed: Boolean, l: Int, t: Int, r: Int, b: Int
    ) {
//        Log.i("orientation","onlayout called")
        val childCount = childCount
        val parentView = parent as ViewGroup

        var childLeft = 0
        val childTop = 0
        var childRight = 0
        var childBottom = 0

        for (i in 0 until childCount) {
            val child: View = getChildAt(i)
            if (child.measuredWidth > parentView.width*0.75) {
                childRight += child.measuredWidth - 380
            } else {
                childRight += child.measuredWidth
            }
            childBottom = child.measuredHeight

            child.layout(
                childLeft,
                childTop,
                childRight,
                childBottom
            )
            childLeft = childRight
        }

    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
//        Log.i("orientation","onMeasure called")
        val parentWidth = MeasureSpec.getSize(widthMeasureSpec)

        var requiredWidth = 0.0
        var requiredHeight = 0
        val childCount = childCount

        for (i in 0 until childCount) {
            val child: TextView = getChildAt(i) as TextView

            child.setPadding(0, 0, 20, 0)
            measureChild(child, widthMeasureSpec, heightMeasureSpec)

            requiredWidth += child.measuredWidth
            var changedWidth = 0.0
            if (child.measuredWidth >= parentWidth*0.75) {
                changedWidth += requiredWidth * 0.25
                requiredWidth += changedWidth
            } else {
                if (requiredHeight < child.measuredHeight) {
                    requiredHeight = child.measuredHeight
                    changedWidth+=requiredWidth
                    requiredWidth+=child.measuredWidth
                }
            }
            child.ellipsize = TextUtils.TruncateAt.END
        }
        setMeasuredDimension(requiredWidth.toInt(), requiredHeight)
    }
}