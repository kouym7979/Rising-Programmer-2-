package com.example.todolist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.google.firebase.firestore.*
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
        var mDatas :ArrayList<List_item> = arrayListOf()

        firestore?.collection("Memo")
            ?.addSnapshotListener(EventListener<QuerySnapshot>(){ querySnapshot: QuerySnapshot?, firebaseFirestoreException: FirebaseFirestoreException? ->
                if(querySnapshot!=null){
                    mDatas.clear()
                    /*for(snap :DocumentSnapshot in querySnapshot.documents){
                        var hash : Map<String, Any> = snap.getData()
                    }*/
                }
            })

       mAdapter.mList=memoList

        mAdapter.notifyDataSetChanged()

        list_recycler.adapter = mAdapter

        return view
    }

}