package com.example.todolist

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_list.*
import kotlinx.android.synthetic.main.fragment_list.view.*
import kotlinx.android.synthetic.main.list_item.*
import kotlinx.android.synthetic.main.list_item.view.*

class ListFragment: Fragment() {

    var firestore: FirebaseFirestore? = null
    private var text_color:String?=null
    private var memo_uid:String?=null
    private var itemTouchHelper :ItemTouchHelper?=null
    private var mAuth:FirebaseAuth?=null
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
            firestore?.collection("sub_memo")
                ?.orderBy("date")?.addSnapshotListener{ querySnapshot, error: FirebaseFirestoreException? ->
                memo_info.clear()
                memo_uid.clear()
                for(snapshot in querySnapshot!!.documents){
                    var item = snapshot.toObject(MemoItem::class.java)//만들어둔 데이터 모델로 매핑됨
                    if(item?.user_uid==uid) {//작성자의 메모만 보인다.
                        memo_info.add(item!!)
                        memo_uid.add(snapshot.id)
                    }
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
            var builder = AlertDialog.Builder(requireContext())
            var list_dialog=layoutInflater.inflate(R.layout.list_dialog,null)

            var ad : AlertDialog = builder.create()
            ad.setView(list_dialog)

            viewHolder.selected_day.text=memo_info!![position].date
            viewHolder.list_memo.text=memo_info!![position].memo
            viewHolder.list_tag.text=memo_info!![position].memo_tag

            viewHolder.list_btn.setOnClickListener{

                val up_btn=list_dialog.findViewById<Button>(R.id.update_Button)
                up_btn.setOnClickListener {

                    ad.dismiss()
                }
                val del_btn=list_dialog.findViewById<Button>(R.id.delete_Button)
                del_btn.setOnClickListener {
                    Log.d("확인:",memo_info!![position].memo_id!!)
                    firestore?.collection("sub_memo")?.document(memo_info!![position].memo_id!!)
                        ?.delete()?.addOnSuccessListener { Log.d("확인","정상적으로 삭제가 되었습니다") }
                    notifyDataSetChanged()
                    Toast.makeText(requireContext(),"삭제되었습니다",Toast.LENGTH_SHORT).show()
                    ad.dismiss()
                }

                ad.show()
            }

            text_color=memo_info!![position].memo_tag

            if(text_color.equals("할일")){
                viewHolder.list_tag.setTextColor(ContextCompat.getColor(requireContext(),R.color.purple_200))
            }
            else if(text_color.equals("프로젝트")){
                viewHolder.list_tag.setTextColor(ContextCompat.getColor(requireContext(),R.color.warningColor))
            }
            else if(text_color.equals("약속")){
                viewHolder.list_tag.setTextColor(ContextCompat.getColor(requireContext(),R.color.welcomeColor))
            }
            else if(text_color.equals("운동")){
                viewHolder.list_tag.setTextColor(ContextCompat.getColor(requireContext(),R.color.purple_500))
            }

        }
        inner class CustomViewHolder(view: View?) : RecyclerView.ViewHolder(view!!) {

        }

    }




}
