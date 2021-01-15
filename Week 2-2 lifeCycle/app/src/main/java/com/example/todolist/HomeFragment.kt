package com.example.todolist

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.*
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*
import java.util.*

class HomeFragment : Fragment(), View.OnClickListener {

    private var select_day: String? = null
    private var memo: String? = null
    private var memo_id: String?= null
    var firestore: FirebaseFirestore? = null
    private var memo_tag:String?=null

    companion object {
        //정적으로 사용되는 부분이 오브젝트이므로
        const val TAG: String = "로그"

        fun newInstance(): HomeFragment {//자기 자신의 인스턴스를 가져옴
            return HomeFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        var items = arrayOf("할일","프로젝트","운동","약속")
        firestore = FirebaseFirestore.getInstance()
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        view.radio_group.setOnCheckedChangeListener { group, checkedId ->
            when(checkedId){
                R.id.r_btn1->{
                    memo_tag="할일"
                }
                R.id.r_btn2->{
                    memo_tag="프로젝트"
                }
                R.id.r_btn3->{
                    memo_tag="운동"
                }
                R.id.r_btn4->{
                    memo_tag="약속"
                }
            }
        }
        view.home_calendar.setOnDateChangeListener { view, year, month, dayOfMonth ->
            select_day = String.format("%d /%d /%d", year, month + 1, dayOfMonth)
            Log.d("확인", "선택한 날짜는" + select_day)
            s_date.text = select_day
        }

        view.btn_write.setOnClickListener(this)

        return view
    }

    var fbAuth: FirebaseAuth? = null

    override fun onClick(v: View?) {

        if(select_day==null){
            Toast.makeText(requireContext(),"날짜를 선택해주세요",Toast.LENGTH_SHORT)
        }else {
            Log.d("확인", "메모 전송 버튼이 눌렸습니다")

            var MemoId = firestore?.collection("Memo")?.document()?.id
            var intent: Intent? = null
            Log.d("확인", "작성된 메모는" + todo_edit.text.toString())

            //파이어베이스에 저장되는 데이터 모델

            var sub_memo = MemoItem()
            sub_memo.date = select_day!!
            sub_memo.memo = todo_edit.text.toString()
            sub_memo.memo_id = MemoId.toString()
            sub_memo.memo_tag = memo_tag

            Log.d("확인", "저장된 데이터: " + sub_memo.date + "메모는: " + sub_memo.memo)

            firestore?.collection("sub_memo")?.document()?.set(sub_memo)

            Toast.makeText(activity, "메모가 등록되었습니다", Toast.LENGTH_SHORT)
            todo_edit.setText("")
        }
    }

    override fun onPause() {
        super.onPause()
        Log.d("확인", "현재 homeFragment onPause입니다")
    }

    override fun onResume() {
        super.onResume()
        Log.d("확인", "현재 homeFragment onResume입니다")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("확인", "현재 homeFragment onDestroy입니다")
    }

    override fun onStop() {
        super.onStop()
        Log.d("확인", "현재 homeFragment onstop입니다")
    }

}