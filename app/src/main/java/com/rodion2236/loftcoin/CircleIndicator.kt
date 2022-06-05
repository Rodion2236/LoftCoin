package com.rodion2236.loftcoin

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.TypedValue
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class CircleIndicator(context: Context) : RecyclerView.ItemDecoration() {

    private val inactivePaint = Paint()
    private val activePaint = Paint()

    private val indicatorRadius: Float

    init {
        val dm = context.resources.displayMetrics
        indicatorRadius = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4f, dm)

        inactivePaint.style = Paint.Style.FILL
        inactivePaint.color = 0x44ffffff
        inactivePaint.isAntiAlias = true

        activePaint.style = Paint.Style.FILL
        activePaint.color = Color.WHITE
        activePaint.isAntiAlias = true
    }

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        val adapter = parent.adapter
        if (adapter != null) {
            val totalWidth = 2 * indicatorRadius * adapter.itemCount
            val posX = (parent.width - totalWidth) / 2f
            val posY = parent.height - 2 * indicatorRadius
            val layoutManager = parent.layoutManager
            var currentIndicator = RecyclerView.NO_POSITION
            if (layoutManager is LinearLayoutManager) {
                currentIndicator = layoutManager.findFirstCompletelyVisibleItemPosition()
            }
            for (i in 0 until adapter.itemCount) {
                drawIndicator(c, posX + 4 * indicatorRadius * i, posY, currentIndicator == i)
            }
        }
    }

    private fun drawIndicator(c: Canvas, x: Float, y: Float, active: Boolean) {
        c.drawCircle(x, y, indicatorRadius, if (active) activePaint else inactivePaint)
    }
}