package com.garosero.android.hobbyroadmap.notice

import android.content.Context
import android.content.res.Resources
import android.graphics.*
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.garosero.android.hobbyroadmap.R
import kotlin.math.roundToInt

class MyItemTouchCallback(
    val adapter : NoticeAdapter,
    val context: Context,
    val resources: Resources?
) : ItemTouchHelper.SimpleCallback(
ItemTouchHelper.UP or ItemTouchHelper.DOWN, ItemTouchHelper.LEFT){
    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return false
    }

    override fun isLongPressDragEnabled(): Boolean {
        return false
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        adapter.removeData(viewHolder.layoutPosition)
    }

    private fun dpToPx(dp: Int):Float{
        val density = context.resources.displayMetrics.density
        val px = (dp * density).roundToInt()
        return px.toFloat()
    }

    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        val itemView = viewHolder.itemView
        val paint = Paint()

        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE){
            if (dX < 0){
                // rect loc
                val rectDst = RectF(
                    itemView.right.toFloat()+dX+0f,
                    itemView.top.toFloat()+0f,
                    itemView.right.toFloat()+0f,
                    itemView.bottom.toFloat()+0f)

                // draw rect
                paint.style = Paint.Style.STROKE
                paint.strokeWidth = dpToPx(3)
                paint.color = Color.parseColor("#FF3D55C2")
                c.drawRect(rectDst, paint)

                // close icon loc
                val icon = BitmapFactory.decodeResource(resources, R.drawable.notice_close)
                val iconSize = dpToPx(24)
                val width = rectDst.right+rectDst.left
                val height = rectDst.bottom+rectDst.top
                val centerHor = width/2
                val centerVer = height/2
                val iconDst = RectF(
                    centerHor-iconSize/2,
                    centerVer-iconSize/2,
                    centerHor+iconSize/2,
                    centerVer+iconSize/2)

                // draw icon
                if (rectDst.right-rectDst.left>=iconSize) {
                    c.drawBitmap(icon, null, iconDst, null)
                }
            }
        }

        super.onChildDraw(
            c,
            recyclerView,
            viewHolder,
            dX,
            dY,
            actionState,
            isCurrentlyActive
        )
    }
}