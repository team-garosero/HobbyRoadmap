package com.garosero.android.hobbyroadmap.view

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import com.garosero.android.hobbyroadmap.R

class EmptyView : LinearLayout {
    private var layout : LinearLayout? = null
    var button : Button? = null

    constructor(context: Context?) : super(context){
        init(context)
    }
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs){
        init(context)
        getAttrs(attrs)

    }
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) :
            super(context, attrs, defStyleAttr){
        init(context)
        getAttrs(attrs,defStyleAttr)
    }

    private fun init(context:Context?){
        val view = LayoutInflater.from(context).inflate(R.layout.item_empty,this,false)
        layout = findViewById(R.id.layout)
        button = findViewById(R.id.btn_find_roadmap)
        button?.setOnClickListener { onClickCallback?.onClick(it) }

        addView(view)
    }

    @SuppressLint("CustomViewStyleable")
    private fun getAttrs(attrs:AttributeSet?){
        val typedArray = context.obtainStyledAttributes(attrs,R.styleable.emptyView)
        setTypeArray(typedArray)
    }

    @SuppressLint("CustomViewStyleable")
    private fun getAttrs(attrs:AttributeSet?, defStyle:Int){
        val typedArray = context.obtainStyledAttributes(attrs,R.styleable.emptyView,defStyle,0)
        setTypeArray(typedArray)
    }

    private fun setTypeArray(typedArray : TypedArray){
        // key string
        val visible = typedArray.getText(R.styleable.emptyView_android_visibility) ?: true
        if (visible as Boolean){
            layout?.visibility = VISIBLE

        } else {
            layout?.visibility = GONE

        } // end if

        typedArray.recycle()
    }

    private var onClickCallback : OnClickCallback? = null
    fun setOnClickCallback(onClickCallback: OnClickCallback){
        this.onClickCallback = onClickCallback
    }
    interface OnClickCallback{
        fun onClick(v : View){
            Log.e("EMPTY", "Click")
        }
    }
}