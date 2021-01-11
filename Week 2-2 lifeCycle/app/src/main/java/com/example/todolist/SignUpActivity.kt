package com.example.todolist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_sign_up.*
import kotlinx.android.synthetic.main.fragment_home.*


class SignUpActivity : AppCompatActivity() {

    var firestore: FirebaseFirestore?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        regButton.setOnClickListener {
            Log.d("확인","가입 메소드 실행 전입니다.")
            createEmail()
        }
    }

    fun createEmail(){
        var name =username.text.toString()
        var email = email_edit.text.toString()
        var pwd = password.text.toString()
        var fbAuth : FirebaseAuth? = null
        Log.d("확인","가입 메소드 실행 중입니다 이메일:${email}. 비밀번호:${pwd}")
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,pwd)
            .addOnCompleteListener { task->
            if(task.isSuccessful){
                Log.d("확인","가입성공")

                var data= hashMapOf<String,Any>()
                var userinfo=List_item()
                data.put(userinfo.email,email_edit.text.toString())//선택날짜
                data.put(userinfo.password,password.text.toString())
                data.put(userinfo.name,username.text.toString())

                firestore?.collection("User")?.add(data)?.addOnSuccessListener { documentReference ->
                    Log.d("확인","작성된 ID: ${documentReference.id}")
                }
                    ?.addOnFailureListener { e->
                        Log.d("확인","에러!",e)
                    }
                finish()
            }
            else {
                Log.d("확인",task.exception.toString())
            }
        }


    }

    override fun onPause() {
        super.onPause()
        Toast.makeText(this,"현재 signup onpause입니다", Toast.LENGTH_SHORT)
        Log.d("확인","현재 SignUp onPause입니다")
    }

    override fun onDestroy() {
        super.onDestroy()
        Toast.makeText(this,"현재 signup ondestroy입니다",Toast.LENGTH_SHORT)
        Log.d("확인","현재 SignUp onDestroy입니다")
    }

}