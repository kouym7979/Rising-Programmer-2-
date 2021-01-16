package com.example.todolist

data class MemoItem(
    var name:String?=null,
    var memo:String?=null,
    var date:String?=null,
    var memo_id : String ?=null,
    var photoUrl: String?=null,
    var memo_tag:String ?=null,
    var user_uid:String?=null

)

data class User_info(
    var name :String?=null,
    var email : String?= null,
    var password : String?= null,
    var uid : String ?=null
)