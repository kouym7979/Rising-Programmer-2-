package com.example.lifecycle

import android.content.Context
import android.nfc.Tag
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

class MypageFragment : Fragment(){//프래그먼트 상속


    companion object{//정적으로 사용되는 부분이 오브젝트이므로
    const val TAG : String ="로그"

        fun newInstance() : MypageFragment{//자기 자신의 인스턴스를 가져옴
            return MypageFragment()
        }
    }

    //메모리에 올라갔을 때
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG,"MypageFragment -onCreate() call")
    }

    //프래그먼트를 품고 있는 액티비티에 붙었을 때
    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.d(TAG,"MypageFragment - onAttach() called")
    }

    //프래그먼트에서는 뷰 생성을 이렇게 함 인플레이터 형식
    //프래그먼트와 레이아웃을 연결시켜주는 부분
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        Log.d(TAG,"MypageFragment-onCreateView() called")

        val view :View = inflater.inflate(R.layout.fragment_my_page,container,false)

        return view
    }
}