package com.example.lifecycle

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.FragmentManager
import com.example.lifecycle.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    //메인 액티비티가 가지고있는 멤버변수
    //lateinit은 나중에 값이 들어가는 것
    private lateinit var homeFragment: HomeFragment
    private lateinit var listFragment: ListFragment
    private lateinit var mypageFragment: MypageFragment
    private lateinit var binding: ActivityMainBinding
    //이렇게 해도 lateinit과 하는 역할은 유사함

    companion object{
        const val TAG: String="로그"
    }

    private var activityMainBinding: ActivityMainBinding?= null

    //화면이 메모리에 올라갔을 때
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        //자동으로 완성된 액티비티 메인 바인딩 클래스 인스턴스를 가져온다.

        val binding : ActivityMainBinding= ActivityMainBinding.inflate(layoutInflater)
        // 뷰 바인딩과 연결
        activityMainBinding =binding
        setContentView(activityMainBinding!!.root)

        Log.d(TAG, "MainActivity - onCreate() called")
        binding.bottomNav.setOnNavigationItemSelectedListener(this)

        //초기화면 설정
        homeFragment = HomeFragment.newInstance()
        supportFragmentManager.beginTransaction().add(R.id.frame_layout, homeFragment).commit()


    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        Log.d(TAG,"Main-navigationItemSelected called")

        when(item.itemId){//switch문과 같은 역할
            R.id.menu_home ->{
                Log.d(TAG,"홈버튼 클릭")
                homeFragment= HomeFragment.newInstance()
                supportFragmentManager.beginTransaction().replace(R.id.frame_layout,homeFragment).commit()

            }
            R.id.menu_list ->{
                Log.d(TAG,"리스트버튼 클릭")
                listFragment= ListFragment.newInstance()
                supportFragmentManager.beginTransaction().replace(R.id.frame_layout,listFragment).commit()

            }
            R.id.menu_mypage ->{
                Log.d(TAG,"마이페이지버튼 클릭")
                mypageFragment= MypageFragment.newInstance()
                supportFragmentManager.beginTransaction().replace(R.id.frame_layout,mypageFragment).commit()

            }
        }

        return true
    }
}
