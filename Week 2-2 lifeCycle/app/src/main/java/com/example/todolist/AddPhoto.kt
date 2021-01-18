package com.example.todolist

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import java.text.SimpleDateFormat
import java.util.*

class AddPhoto : AppCompatActivity() {
    var PICK_IMAGE_FROM_ALBUM=0
    var photo_uri : Uri?=null
    var storage : FirebaseStorage?=null
    var firestore: FirebaseFirestore?=null
    private var memo_id:String?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_photo)

        storage = FirebaseStorage.getInstance()
        firestore = FirebaseFirestore.getInstance()

        var photoPickerIntent = Intent(Intent.ACTION_PICK)
        photoPickerIntent.type="image/*"

        startActivityForResult(photoPickerIntent, PICK_IMAGE_FROM_ALBUM)
        Log.d("확인", "add photo onCreate부분입니다.")

        /*var intent:Intent?=null
        memo_id=intent?.getStringExtra("memoid")
*/
        contentUpload()
    }

    override fun onResume() {
        super.onResume()
        Log.d("확인", "add photo onResume부분입니다.")
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.d("확인","addphoto에 activity result입니다.")
        if(requestCode==PICK_IMAGE_FROM_ALBUM){
            if(requestCode== Activity.RESULT_OK) {
                photo_uri=data?.data
                Log.d("확인","선택된 사진:"+photo_uri.toString())

            }else finish()

        }
    }


    fun contentUpload(){

        val timeStamp=SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val imageFileName="JPEG_"+timeStamp+"_.png"
        Log.d("확인","사진 업로드 메소드입니다")
        val storeRef=storage?.reference?.child("sub_memo")?.child(imageFileName)
        storeRef?.putFile(photo_uri!!)?.addOnSuccessListener { taskSnapshot ->
            Log.d("확인", "사진이 업로드 되었습니다")

            firestore?.collection("sub_memo")?.document(memo_id!!)
                ?.update("photoUrl", photo_uri)
            var intent=Intent(this,MainActivity::class.java)
            intent.putExtra("url",photo_uri.toString())
            setResult(Activity.RESULT_OK)

            finish()
        }?.addOnFailureListener {
          Log.d("확인", "사진 업로드에 실패했습니다")
        }
    }
}