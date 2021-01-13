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

    private var firestore: FirebaseFirestore? = null
    private var fbAuth: FirebaseAuth? = null
    private var name: String? = null
    private var email: String? = null
    private var pwd: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        firestore= FirebaseFirestore.getInstance()
        fbAuth= FirebaseAuth.getInstance()
        regButton.setOnClickListener {
            Log.d("확인", "가입 메소드 실행 전입니다.")
            createEmail()
        }
    }

    fun createEmail() {
        name = username.text.toString()
        email = email_edit.text.toString()
        pwd = password.text.toString()
        Log.d("확인", "가입 메소드 실행 중입니다 이메일:${email}. 비밀번호:${pwd}")

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email_edit.text.toString(), password.text.toString())
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("확인", "가입성공")
                    var data = User_info()
                    data.name = username.text.toString()
                    data.email = email_edit.text.toString()
                    data.password = password.text.toString()
                    data.uid = fbAuth?.currentUser?.uid
                    Log.d("확인", "회원가입 메소드 확인" + data.name + " uid:" + data.uid +" "+ data.email+" "+data.password)
                    firestore?.collection("User")?.document()?.set(data)
                        ?.addOnSuccessListener { documentReference ->
                            Log.d("확인", "작성된 ID: ${data.uid}")
                        }
                        ?.addOnFailureListener { e ->
                            Log.d("확인", "에러!", e)
                        }
                    var intent = Intent(this,LoginActivity::class.java)
                    intent.putExtra("email",data.email)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    startActivity(intent)
                } else {
                    Log.d("확인", task.exception.toString())
                }
            }


    }

    override fun onPause() {
        super.onPause()
        Toast.makeText(this, "현재 signup onpause입니다", Toast.LENGTH_SHORT)
        Log.d("확인", "현재 SignUp onPause입니다")
    }

    override fun onDestroy() {
        super.onDestroy()
        Toast.makeText(this, "현재 signup ondestroy입니다", Toast.LENGTH_SHORT)
        Log.d("확인", "현재 SignUp onDestroy입니다")
    }

}