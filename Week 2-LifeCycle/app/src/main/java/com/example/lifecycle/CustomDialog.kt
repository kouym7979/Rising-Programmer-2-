package com.example.lifecycle

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.dialog.*

class CustomDialog(context: Context, selected_date :String) : Dialog(context),
    View.OnClickListener {

    val TAG: String="로그"
    var s_date:String=selected_date

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.dialog)

        findViewById<TextView>(R.id.select_date).text=s_date
        Log.d("TAG","선택된 날짜입니다"+s_date)
        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        yesButton.setOnClickListener(this)
        noButton.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v){
            yesButton->{
                Log.d("확인","예스버튼이 눌렸습니다.")
            }
            noButton->{

            }
        }
    }


}