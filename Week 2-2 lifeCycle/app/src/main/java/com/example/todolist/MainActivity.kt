package com.example.todolist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import com.example.todolist.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    private lateinit var homeFragment: HomeFragment
    private lateinit var listFragment: ListFragment
    private lateinit var mypageFragment: MypageFragment

    private var activityMainBinding: ActivityMainBinding?= null
    //private var bottomNav =findViewById<BottomNavigationView>(R.id.bottomNav)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setFrag()
        val binding : ActivityMainBinding= ActivityMainBinding.inflate(layoutInflater)
        // 뷰 바인딩과 연결
        activityMainBinding =binding
        setContentView(activityMainBinding!!.root)

        Log.d("확인", "MainActivity - onCreate() called")
        binding.bottomNav.setOnNavigationItemSelectedListener(this)

        //초기화면 설정
        homeFragment = HomeFragment.newInstance()
        supportFragmentManager.beginTransaction().add(R.id.frame_layout, homeFragment).commit()

    }

    private fun setFrag() {

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.menu_home -> {
                Log.d("확인", "홈버튼 클릭")
                homeFragment= HomeFragment.newInstance()
                supportFragmentManager.beginTransaction().replace(R.id.frame_layout, homeFragment).commit()

            }
            R.id.menu_list -> {
                Log.d("확인", "리스트버튼 클릭")
                listFragment= ListFragment.newInstance()
                supportFragmentManager.beginTransaction().replace(R.id.frame_layout, listFragment).commit()

            }
            R.id.menu_mypage -> {
                Log.d("확인", "마이페이지버튼 클릭")
                mypageFragment= MypageFragment.newInstance()
                supportFragmentManager.beginTransaction().replace(R.id.frame_layout, mypageFragment).commit()
            }
        }
        return true
    }
}