package com.example.todolist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

class ListFragment: Fragment(){
    companion object{//정적으로 사용되는 부분이 오브젝트이므로
    const val TAG : String ="로그"

        fun newInstance() : ListFragment{//자기 자신의 인스턴스를 가져옴
            return ListFragment()
        }
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_list,container,false)
        return view
    }
}