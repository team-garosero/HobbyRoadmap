package com.garosero.android.hobbyroadmap.settings

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.Switch
import android.widget.TextView
import com.garosero.android.hobbyroadmap.R
import com.garosero.android.hobbyroadmap.data.TilBoxItem

class CustomSettingView: LinearLayout {
    lateinit var layout: LinearLayout
    lateinit var tvKey: TextView
    lateinit var tvValue: TextView
    lateinit var switch: Switch
    lateinit var imageButton: ImageButton

    constructor(context: Context?) : super(context){
        connector(context, null)
    }
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs){
        val typedArray = context?.obtainStyledAttributes(attrs,R.styleable.SettingsItem)
        connector(context, typedArray)
    }
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) :
            super(context, attrs, defStyleAttr){
        val typedArray = context?.obtainStyledAttributes(attrs,R.styleable.SettingsItem,defStyleAttr,0)
        connector(context, typedArray)
    }

    private fun connector(context:Context?, typedArray : TypedArray?){
        val type = typedArray?.getText(R.styleable.SettingsItem_type)
        type?:return
        if (type.equals("switch")){
            initSwitch(context)
            setTypeArraySwitch(typedArray)
        } else if (type.equals("imageButton")){
            initImageButton(context)
            setTypeArrayImageButton(typedArray)
        } else {
            initTextView(context)
            setTypeArrayTextView(typedArray)
        }
    }

    // type1
    private fun initTextView(context:Context?){
        val view = LayoutInflater.from(context).inflate(R.layout.customview_settings_textview,this,false)
        addView(view)

        layout = findViewById(R.id.layout)
        tvKey = findViewById(R.id.tv_key)
        tvValue = findViewById(R.id.tv_value)
    }

    private fun setTypeArrayTextView(typedArray : TypedArray){
        // key string
        val keyString = typedArray.getText(R.styleable.SettingsItem_keyString)
        tvKey.text = keyString

        // value string
        val valueString = typedArray.getText(R.styleable.SettingsItem_valueString)
        tvValue.text = valueString

        // layout onclick
        layout.setOnClickListener {
            listener?.onItemClick(it)
        }

        typedArray.recycle()
    }

    // type2
    private fun initSwitch(context:Context?){
        val view = LayoutInflater.from(context).inflate(R.layout.customview_settings_switch,this,false)
        addView(view)

        layout = findViewById(R.id.layout)
        tvKey = findViewById(R.id.tv_key)
        switch = findViewById(R.id.switch_value)
    }

    private fun setTypeArraySwitch(typedArray : TypedArray){
        // key string
        val keyString = typedArray.getText(R.styleable.SettingsItem_keyString)
        tvKey.text = keyString

        // switch
        setSwitchText()
        switch.setOnClickListener {
            setSwitchText()
        }

        // layout onclick
        layout.setOnClickListener {
            listener?.onItemClick(it)
        }

        typedArray.recycle()
    }

    private fun setSwitchText(){
        if (switch.isChecked) switch.text = switch.textOn
        else switch.text = switch.textOff
    }

    // type3
    private fun initImageButton(context:Context?){
        val view = LayoutInflater.from(context).inflate(R.layout.customview_settings_imagebutton,this,false)
        addView(view)

        layout = findViewById(R.id.layout)
        tvKey = findViewById(R.id.tv_key)
        imageButton = findViewById(R.id.ibn_value)
    }

    private fun setTypeArrayImageButton(typedArray : TypedArray){
        // key string
        val keyString = typedArray.getText(R.styleable.SettingsItem_keyString)
        tvKey.text = keyString

        // layout onclick
        layout.setOnClickListener {
            listener?.onItemClick(it)
        }

        typedArray.recycle()
    }

    // on click interface
    interface OnItemClickListener{
        fun onItemClick(view: View)
    }
    private var listener : OnItemClickListener? = null
    fun setOnItemClickListener(listener : OnItemClickListener) {
        this.listener = listener
    }
}