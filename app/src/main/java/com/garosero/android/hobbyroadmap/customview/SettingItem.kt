package com.garosero.android.hobbyroadmap.customview

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.Switch
import android.widget.TextView
import com.garosero.android.hobbyroadmap.R

class SettingItem : LinearLayout {
    lateinit var layout : LinearLayout
    lateinit var tvKey : TextView
    lateinit var tvValue : TextView

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
        val view = LayoutInflater.from(context).inflate(R.layout.item_settings,this,false)
        addView(view)

        layout = findViewById(R.id.layout)
        tvKey = findViewById(R.id.tv_key)
        tvValue = findViewById(R.id.tv_value)
    }

    private fun getAttrs(attrs:AttributeSet?){
        val typedArray = context.obtainStyledAttributes(attrs,R.styleable.SettingsItem)
        setTypeArray(typedArray)
    }

    private fun getAttrs(attrs:AttributeSet?, defStyle:Int){
        val typedArray = context.obtainStyledAttributes(attrs,R.styleable.SettingsItem,defStyle,0)
        setTypeArray(typedArray)
    }

    private fun setTypeArray(typedArray : TypedArray){
        // key string
        val keyString = typedArray.getText(R.styleable.SettingsItem_keyString)
        val keyColor = typedArray.getColor(R.styleable.SettingsItem_keyTextColor,0)
        tvKey.text = keyString
        tvKey.setTextColor(keyColor)

        // value string
        val valueString = typedArray.getText(R.styleable.SettingsItem_valueString)
        val valueColor = typedArray.getColor(R.styleable.SettingsItem_valueTextColor,0)
        tvValue.text = valueString
        tvValue.setTextColor(valueColor)

        typedArray.recycle()
    }
}