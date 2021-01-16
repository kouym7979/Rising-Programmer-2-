package com.example.todolist

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.fragment_home.*

class AddPhoto : AppCompatActivity() {
    var PICK_IMAGE_FROM_ALBUM=0
    var photo_url :String?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_photo)


        var photoPickerIntent = Intent(Intent.ACTION_PICK)
        photoPickerIntent.type="image/*"
        startActivityForResult(photoPickerIntent,PICK_IMAGE_FROM_ALBUM)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode==PICK_IMAGE_FROM_ALBUM){
            if(requestCode== Activity.RESULT_OK) {
                memo_image.setImageURI(data?.data)
                photo_url=data?.data.toString()
                var intent = Intent(this,MainActivity::class.java)
                intent?.putExtra("url",photo_url)
                intent?.putExtra("p_url",data?.data)
                intent?.putExtra("check",1)//1이 사진이 있다는 것
                startActivity(intent)
                Log.d("확인","사진이 저장되었습니다.")
            }

        }
    }
}