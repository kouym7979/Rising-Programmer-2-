package com.example.todolist

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.ActionMode
import android.view.View
import android.widget.CheckBox
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_sign_up.*

class LoginActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var mAuth:FirebaseAuth
    private  var currentUser :FirebaseUser?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        mAuth= FirebaseAuth.getInstance()

        btn_register.setOnClickListener(this)
        btn_login.setOnClickListener(this)

        var pref :SharedPreferences = getSharedPreferences("pref",Activity.MODE_PRIVATE)
        btn_chklogin.isChecked=pref.getBoolean("autocheck",false)
    }

    override fun onClick(v: View?) {

        when(v){
            btn_register->{
                val intent = Intent(this,SignUpActivity::class.java)
                startActivity(intent)
            }
            btn_login->{
                Log.d("확인","Login Activity 로그인 버튼"+emailEdit.text.toString()+" "+passEdit.text.toString())
               loginStart(emailEdit.text.toString(),passEdit.text.toString())

            }
        }
    }

    override fun onStart() {
        super.onStart()
        currentUser=mAuth.currentUser
        if(btn_chklogin.isChecked==true){
            if(currentUser!=null)
            {
                startActivity(Intent(this,MainActivity::class.java))
                finish()
            }
        }
    }

    override fun onStop() {
        super.onStop()
        var pref :SharedPreferences =getSharedPreferences("pref", Activity.MODE_PRIVATE)
        var editor : SharedPreferences.Editor = pref.edit()
        editor.putBoolean("autocheck",btn_chklogin.isChecked)
        editor.commit()
    }

    private fun loginStart(email:String, pws:String){
        Log.d("확인","Login Activity 로그인 버튼"+emailEdit.text.toString()+" "+passEdit.text.toString())
        mAuth.signInWithEmailAndPassword(emailEdit.text.toString(),passEdit.text.toString())
            .addOnCompleteListener(this){task->
                if(task.isSuccessful){
                    Log.d("확인","로그인에 성공했습니다")
                    var intent : Intent = Intent(this,MainActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    startActivity(intent)
                }
                else{
                    Log.d("확인",task.exception.toString())
                }

            }
    }

}

