package com.example.todolist

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*
import java.util.*

class HomeFragment:Fragment(), View.OnClickListener {

    private var select_day: String? = null
    private var memo: String?= null
    var firestore: FirebaseFirestore?=null

    companion object {
        //정적으로 사용되는 부분이 오브젝트이므로
        const val TAG: String = "로그"

        fun newInstance(): HomeFragment {//자기 자신의 인스턴스를 가져옴
            return HomeFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        firestore= FirebaseFirestore.getInstance()
        val view = inflater.inflate(R.layout.fragment_home,container,false)

        view.home_calendar.setOnDateChangeListener { view, year, month, dayOfMonth ->
            select_day = String.format("%d /%d /%d", year, month + 1, dayOfMonth)
            Log.d("확인", "선택한 날짜는" + select_day)
            s_date.text=select_day
        }



        view.write_btn.setOnClickListener(this)

        return view
    }

    override fun onClick(v: View?) {
        var MemoId=firestore?.collection("Memo")?.document()?.id
        var intent: Intent?=null
        Log.d("확인","작성된 메모는"+todo_edit.text.toString())
        var memoInfo=List_item()
        var data= hashMapOf<String,String>()

        data.put(memoInfo.date!!,select_day!!)//선택날짜
        data.put(memoInfo.memo!!,todo_edit.text.toString())//작성된 메모

        firestore?.collection("Memo")?.add(data)?.addOnSuccessListener { documentReference ->
            Log.d("확인","작성된 ID: ${documentReference.id}")
        }
            ?.addOnFailureListener { e->
                Log.d("확인","에러!",e)
            }

    }
}