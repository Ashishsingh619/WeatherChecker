package com.example.weatherforecast

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.FrameLayout
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView

class NavigationActivity : AppCompatActivity() {
    lateinit var toolbar: Toolbar
    lateinit var drawerLayout: DrawerLayout
    lateinit var Navigationview: NavigationView
    lateinit var Frame: FrameLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigation)
        toolbar = findViewById(R.id.toolbar)
        drawerLayout = findViewById(R.id.drawer)
        Navigationview = findViewById(R.id.navigation)
        Frame = findViewById(R.id.frame)
        setAction()
        Homefragment()
        val actionDrawer = ActionBarDrawerToggle(
            this@NavigationActivity,
            drawerLayout,
            toolbar,
            R.string.open_drawer,
            R.string.close_drawer
        )

        drawerLayout.addDrawerListener(actionDrawer)
        actionDrawer.syncState()
        Navigationview.setNavigationItemSelectedListener {
            if (R.id.itm_home == it.itemId) {
                Homefragment()
            }
            if (R.id.itm_Setting == it.itemId) {
                supportFragmentManager.beginTransaction().replace(R.id.frame, SettingFragment())
                    .commit()
                drawerLayout.closeDrawers()
                supportActionBar?.title = "Settings"
            }
            if (R.id.itm_about == it.itemId) {
                supportFragmentManager.beginTransaction().replace(R.id.frame, AboutFragment())
                    .commit()
                drawerLayout.closeDrawers()
                supportActionBar?.title = "About"
            }
            if (R.id.itm_maps == it.itemId) {
                var intent = Intent(this@NavigationActivity, MapsActivity::class.java)
                startActivity(intent)
            }
            return@setNavigationItemSelectedListener true
        }

    }

    fun setAction() {
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Toolbar"
        supportActionBar?.setHomeButtonEnabled(true)
    }

    fun Homefragment() {
        supportFragmentManager.beginTransaction().replace(R.id.frame, HomeFragment()).commit()
        drawerLayout.closeDrawers()
        supportActionBar?.title = "Home"
    }

    override fun onBackPressed() {
        var checkSet = supportFragmentManager.findFragmentById(R.id.frame)
        if (checkSet != HomeFragment()) {
            Homefragment()
        } else
            super.onBackPressed()
    }
}
