package com.example.todolist

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
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



        view.btn_write.setOnClickListener(this)

        return view
    }
    var fbAuth : FirebaseAuth? = null

    override fun onClick(v: View?) {

        if (select_day == null) {
            Toast.makeText(activity, "날짜를 선택해주세요", Toast.LENGTH_SHORT)
        } else {
            var MemoId = firestore?.collection("Memo")?.document()?.id
            var intent: Intent? = null
            Log.d("확인", "작성된 메모는" + todo_edit.text.toString())
            var memoInfo = List_item()

            var data = hashMapOf<String, Any>()
            Log.d("date", select_day)
            Log.d("memo", todo_edit.text.toString())
            data.put(memoInfo.date, select_day!!)//선택날짜 data.put("memo",todo_edit.text.toString())
            data.put(memoInfo.memo, todo_edit.text.toString())

            Log.d("확인", memoInfo.date)
            Log.d("확인", memoInfo.memo)

            //firestore?.collection("Memo")?.document()?.set(memoInfo)
            //firestore?.collection("Memo")?.document(fbAuth?.uid.toString())?.set(data)
            firestore?.collection("Memo")?.add(data)?.addOnSuccessListener { documentReference ->
                Log.d("확인", "작성된 ID: ${documentReference.id}")
            }
                ?.addOnFailureListener { e ->
                    Log.d("확인", "에러!", e)
                }


            Toast.makeText(activity, "메모가 등록되었습니다", Toast.LENGTH_SHORT)
            todo_edit.setText("")
        }
    }
}