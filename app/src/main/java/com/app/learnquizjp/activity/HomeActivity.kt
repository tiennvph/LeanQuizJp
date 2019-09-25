package com.app.learnquizjp.activity

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.app.learnquizjp.R
import com.app.learnquizjp.fragment.*
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.app_bar_home.*
import kotlinx.android.synthetic.main.nav_header_home.view.*


class HomeActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setSupportActionBar(toolbar)
        loadFragment(HomeFragment())
        val toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar,
            com.app.learnquizjp.R.string.navigation_drawer_open,
            com.app.learnquizjp.R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
        nav_view.setNavigationItemSelectedListener(this)
        getUserInformation()
        //val storage = FirebaseStorage.getInstance()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.home, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when {
            item.itemId == R.id.action_favorite -> {
                loadFragment(FavoriteFragment())
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        var fragment : Fragment
        var auth : FirebaseAuth = FirebaseAuth.getInstance()
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_home -> {
                fragment = HomeFragment()
                loadFragment(fragment)
            }
            R.id.nav_learning -> startActivity(Intent(this, LearningActivity::class.java))
            R.id.nav_quiz -> startActivity(Intent(this,QuizActivity::class.java))
            R.id.nav_setting -> {
                fragment = SettingFragment()
                loadFragment(fragment)
            }
            R.id.nav_feedback -> {
//                fragment = FeedbackFragment()
//                loadFragment(fragment)
                FeedbackFragment().show(supportFragmentManager,"Feedback")
            }
            R.id.nav_about -> {
                fragment = AboutFragment()
                loadFragment(fragment)
            }
            R.id.nav_log_out -> {
                startActivity(Intent(this@HomeActivity,LoginActivity::class.java))
                finish()
            }
        }
        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun getUserInformation(){
        var fragment = UserFragment()
        var sharedPreferences = getSharedPreferences("USER_ACCOUNT",MODE_PRIVATE)
        var email : String = sharedPreferences.getString("EMAIL","")
        val navigationView = findViewById<NavigationView>(R.id.nav_view)
        val header = navigationView.getHeaderView(0)
        header.tvEmail.text = email
        header.imgUserAva.setOnClickListener {
            loadFragment(fragment)
            drawer_layout.closeDrawer(GravityCompat.START,false)
        }
    }

    private fun loadFragment(fragment : Fragment){
        var fragmentManager : FragmentManager = supportFragmentManager
        var fragmentTransaction : FragmentTransaction = fragmentManager.beginTransaction()
        if(fragment.isAdded){
            fragmentTransaction.show(fragment)
        }else{
            fragmentTransaction.replace(R.id.container,fragment)
        }
        fragmentTransaction.commit()
    }

}
