package com.example.lifecycle

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.widget.TextView

class CustomDialog(context: Context, selected_date :String) : Dialog(context){

    val TAG: String="로그"
    var s_date:String=selected_date

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.dialog)

        findViewById<TextView>(R.id.select_date).text=s_date
        Log.d("TAG","선택된 날짜입니다"+s_date)
        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }


}