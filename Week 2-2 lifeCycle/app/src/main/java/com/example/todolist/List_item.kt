package com.example.todolist

data class List_item(//코틀린에서는 이 자체로 java처럼 get set이 형성이 됨
    var date :String="date",
    var memo: String="memo",

    var name: String="name",
    var email:String="email",
    var password: String="password",

    var index :Int =0
)
