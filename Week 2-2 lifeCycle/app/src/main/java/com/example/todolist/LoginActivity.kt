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
import android.widget.Toast
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
                if(passEdit.text.toString()!=null && emailEdit.text.toString()!=null) {
                    loginStart(emailEdit.text.toString(), passEdit.text.toString())
                }else
                {
                    Toast.makeText(this,"아이디 및 비밀번호를 확인해주세요.",Toast.LENGTH_SHORT)
                }
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
        if(email.equals("") || pws.equals(""))
        {
            Log.d("확인","이메일:"+email+"비밀번호:"+pws)
            Toast.makeText(this,"이메일 및 비밀번호를 확인하세요",Toast.LENGTH_SHORT).show()
        }else {
            mAuth.signInWithEmailAndPassword(emailEdit.text.toString(), passEdit.text.toString())
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Log.d("확인", "로그인에 성공했습니다")
                        var intent: Intent = Intent(this, MainActivity::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                        startActivity(intent)
                    } else {
                        Log.d("확인", task.exception.toString())
                    }

                }
        }
    }

    override fun onResume() {
        super.onResume()
        //회원가입 onpause이후에 onresume이 실행됨 이때 가입된 아이디를 가져오기

        Toast.makeText(this,"현재 Login onResume입니다",Toast.LENGTH_SHORT)
        //회원가입 후 넘어 왔을 때 가입한 이메일이 입력됩니다.
        var email: String =intent?.getStringExtra("email").toString()
        if(email.equals("null"))
            emailEdit.setText("")
        else emailEdit.setText(email)

    }

    override fun onRestart() {
        super.onRestart()
        Toast.makeText(this,"현재 Login onRestart입니다",Toast.LENGTH_SHORT)
    }
    override fun onDestroy() {
        super.onDestroy()
        Toast.makeText(this,"현재 Login onDestory입니다",Toast.LENGTH_SHORT)
        Log.d("확인","현재 Logtin onDestroy입니다")
    }
}

