package com.example.todolist

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.databinding.ActivityAddPhotoBinding.inflate
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_home.view.*
import kotlinx.android.synthetic.main.fragment_list.*
import kotlinx.android.synthetic.main.fragment_list.view.*
import kotlinx.android.synthetic.main.list_item.*
import kotlinx.android.synthetic.main.list_item.view.*
import kotlinx.android.synthetic.main.update_dialog.*

class ListFragment: Fragment() {

    var firestore: FirebaseFirestore? = null
    private var text_color:String?=null
    private var memo_uid:String?=null
    private var itemTouchHelper :ItemTouchHelper?=null
    private var mAuth:FirebaseAuth?=null
    private var memo_tag:String?=null
    private var u_memo:String ?= null
    private var filter:String?=null
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
        arguments?.let {
            filter=it.getString("filter")
        }
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
            firestore?.collection("sub_memo")!!.whereEqualTo("memo_tag","운동")
                ?.orderBy("date")?.addSnapshotListener{ querySnapshot, error: FirebaseFirestoreException? ->
                memo_info.clear()
                memo_uid.clear()
                for(snapshot in querySnapshot!!.documents){//여기 부분 해결해야함
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
                //수정버튼을 눌렀을 때!
                up_btn.setOnClickListener {
                    ad.dismiss()
                    var update_builder = AlertDialog.Builder(requireContext())
                    var update_dialog=layoutInflater.inflate(R.layout.update_dialog,null)
                    var update_ad :AlertDialog=update_builder.create()
                    update_ad.setView(update_dialog)

                    val u_edit=update_dialog.findViewById<EditText>(R.id.list_memo_edit)
                    val up_yes_btn=update_dialog.findViewById<Button>(R.id.update_yesButton)
                    val up_no_btn=update_dialog.findViewById<Button>(R.id.update_noButton)

                    up_yes_btn.setOnClickListener{
                        u_memo=u_edit.text.toString()
                        firestore?.collection("sub_memo")?.document(memo_info!![position].memo_id!!)
                            ?.update("memo",u_memo)

                        notifyDataSetChanged()
                        Toast.makeText(requireContext(),"수정이 완료되었습니다",Toast.LENGTH_SHORT).show()
                        update_ad.dismiss()
                    }
                    up_no_btn.setOnClickListener {
                       update_ad.dismiss()
                    }

                    update_ad.show()
                }
                //삭제 버튼을 눌렀을 때!!
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

            changeColor(text_color.toString(),viewHolder.list_tag)


            viewHolder.list_memo.setOnClickListener {
                var all_builder = AlertDialog.Builder(requireContext())
                var all_dialog=layoutInflater.inflate(R.layout.all_dialog,null)
                var all_ad :AlertDialog=all_builder.create()
                all_ad.setView(all_dialog)

                var all_date = all_dialog.findViewById<TextView>(R.id.all_date)
                var all_memo = all_dialog.findViewById<TextView>(R.id.all_memo)
                var all_tag= all_dialog.findViewById<TextView>(R.id.all_tag)
                var all_btn=all_dialog.findViewById<ImageButton>(R.id.all_btn)

                all_date.text=memo_info!![position].date
                all_memo.text=memo_info!![position].memo
                all_tag.text=memo_info!![position].memo_tag

                changeColor(memo_info!![position].memo_tag.toString(),all_tag)

                all_btn.setOnClickListener {
                    all_ad.dismiss()
                }

                all_ad.show()
            }
        }
        inner class CustomViewHolder(view: View?) : RecyclerView.ViewHolder(view!!) {

        }

    }

    override fun onPause() {
        super.onPause()
        Log.d("확인", "현재 listFragment onPause입니다")
    }

    override fun onResume() {
        super.onResume()

        Log.d("확인", "현재 listFragment onResume입니다"+filter.toString())
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("확인", "현재 listFragment onDestroy입니다")
    }

    override fun onStop() {
        super.onStop()
        Log.d("확인", "현재 listFragment onstop입니다")
    }

    fun changeColor(check : String, tag : TextView){
        if(check.equals("할일")){
            tag.setTextColor(ContextCompat.getColor(requireContext(),R.color.purple_200))
        }
        else if(check.equals("프로젝트")){
            tag.setTextColor(ContextCompat.getColor(requireContext(),R.color.warningColor))
        }
        else if(check.equals("약속")) {
            tag.setTextColor(ContextCompat.getColor(requireContext(), R.color.welcomeColor))
        }
        else if(check.equals("운동")){
            tag.setTextColor(ContextCompat.getColor(requireContext(),R.color.purple_500))
        }
    }
}
