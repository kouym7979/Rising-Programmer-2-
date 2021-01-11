package com.example.todolist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_list.*

class ListFragment: Fragment() {

    var firestore: FirebaseFirestore? = null
    var memoList: ArrayList<List_item> = arrayListOf()

    companion object {
        //정적으로 사용되는 부분이 오브젝트이므로
        const val TAG: String = "로그"

        fun newInstance(): ListFragment {//자기 자신의 인스턴스를 가져옴
            return ListFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_list, container, false)
        //firestore 인스턴스 초기화
        firestore = FirebaseFirestore.getInstance()
        var mAdapter = RecyclerAdapter(requireContext())

        firestore?.collection("Memo")
            ?.addSnapshotListener { querySanpshot, firebaseFirestoreException ->
                memoList.clear()

                for (snapshot in querySanpshot!!.documents) {
                    var item = snapshot.toObject(List_item::class.java)
                    memoList.add(item!!)
                }

            }

       mAdapter.mList=memoList

        mAdapter.notifyDataSetChanged()

        list_recycler.adapter = mAdapter

        return view
    }

}