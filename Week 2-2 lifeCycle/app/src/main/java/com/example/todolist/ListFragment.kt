package com.example.todolist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*
import kotlinx.android.synthetic.main.fragment_list.*
import kotlinx.android.synthetic.main.fragment_list.view.*
import kotlinx.android.synthetic.main.list_item.*
import kotlinx.android.synthetic.main.list_item.view.*

class ListFragment: Fragment() {

    var firestore: FirebaseFirestore? = null

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
        firestore = FirebaseFirestore.getInstance()
        val view = LayoutInflater.from(inflater.context).inflate(R.layout.fragment_list, container, false)
        //firestore 인스턴스 초기화
        view.list_recycler.adapter= ListRecyclerviewAdapter()
        view.list_recycler.layoutManager= LinearLayoutManager(activity)

        return view
    }
    inner class ListRecyclerviewAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {

        val memo_info : ArrayList<MemoItem> // 문서의 해당 uid안에 있는 컨텐츠 정보를 담고 있는 리스트
        val memo_uid : ArrayList<String>//문서의 uid 관리

        init{
            memo_info = ArrayList()
            memo_uid= ArrayList()

            //현재 로그인된 유저의 uid
            var uid = FirebaseAuth.getInstance().currentUser?.uid
            firestore?.collection("sub_memo")?.orderBy("date")?.addSnapshotListener{ querySnapshot, error: FirebaseFirestoreException? ->
                memo_info.clear()
                memo_uid.clear()
                for(snapshot in querySnapshot!!.documents){
                    var item = snapshot.toObject(MemoItem::class.java)//만들어둔 데이터 모델로 매핑됨
                    memo_info.add(item!!)
                    memo_uid.add(snapshot.id)

                }
                notifyDataSetChanged()//데이터베이스가 변경될 때마다 새로고침 됨
            }

        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            var view = LayoutInflater.from(parent.context).inflate(R.layout.list_item,parent,false)

            return CustomViewHolder(view)
        }

        override fun getItemCount(): Int {
            return memo_info.size;
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
           var viewHolder = (holder as CustomViewHolder).itemView

            viewHolder.selected_day.text=memo_info!![position].date
            viewHolder.list_memo.text=memo_info!![position].memo
            viewHolder.list_tag.text=memo_info!![position].memo_tag

        }
        inner class CustomViewHolder(view: View?) : RecyclerView.ViewHolder(view!!) {

        }

    }

    

}
