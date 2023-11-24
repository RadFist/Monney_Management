package com.example.moneyflow

import android.app.NativeActivity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Switch
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity :BottomNavigationView.OnNavigationItemSelectedListener,AppCompatActivity()  {

lateinit var button_nav:BottomNavigationView
private var homeFragment:home = home()
private var diagramFragment:diagram_Fragment = diagram_Fragment()
private  var monneyflowFragment:BlankFragment = BlankFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportFragmentManager.beginTransaction().replace(R.id.fillFragment,homeFragment).commit()
        setContentView(R.layout.activity_main)
        button_nav = findViewById(R.id.bottomNavigationView)
        button_nav.setOnItemSelectedListener(this)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.monney -> {supportFragmentManager.beginTransaction().replace(R.id.fillFragment,monneyflowFragment).commit(); return true}
            R.id.home -> {supportFragmentManager.beginTransaction().replace(R.id.fillFragment,homeFragment).commit(); return true}
            R.id.chart -> {supportFragmentManager.beginTransaction().replace(R.id.fillFragment,diagramFragment).commit(); return true}
        }
        return false
    }
}