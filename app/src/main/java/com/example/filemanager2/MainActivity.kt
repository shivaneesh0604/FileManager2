package com.example.filemanager2

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.SearchView
import android.widget.Toolbar
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var drawerLayout: DrawerLayout
    private val allFragment: AllFragment = AllFragment()
    private val aboutUsActivity: AboutUsActivity = AboutUsActivity()
    private lateinit var toolbar: MaterialToolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        drawerLayout = findViewById(R.id.drawerLayoutMain)
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        toggle =
            ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.start, R.string.end)

        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        val navigationView = findViewById<NavigationView>(R.id.navigation_view_main)
        navigationView.setNavigationItemSelectedListener(this)

        if (savedInstanceState != null) {
            Log.i("tag1", "saved state instance in Main Activity")
            val fragmentTag = savedInstanceState.getString("fragmentTag")
            val currentFragment = supportFragmentManager.findFragmentByTag(fragmentTag)
            if (currentFragment != null) {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.drawerSwitch, currentFragment)
                    .commit()
            }
        } else {
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.drawerSwitch, allFragment)
                commit()
            }
        }

    }

    override fun onStart() {
        super.onStart()
        if (supportFragmentManager.findFragmentById(R.id.drawerSwitch)!! is AllFragment || supportFragmentManager.findFragmentById(
                R.id.drawerSwitch
            )!! is DetailsFragment
        ) {
            supportActionBar?.apply {
                setDisplayHomeAsUpEnabled(false)
            }
        } else {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        Log.i("query_check", "came on nav")
        when (item.itemId) {
            R.id.home ->
                supportFragmentManager.beginTransaction().replace(R.id.drawerSwitch, allFragment)
                    .commit()
            R.id.about_us ->
                supportFragmentManager.beginTransaction()
                    .replace(R.id.drawerSwitch, aboutUsActivity)
                    .commit()
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        if (supportFragmentManager.findFragmentById(R.id.drawerSwitch) != null) {
            outState.putString(
                "fragmentTag",
                supportFragmentManager.findFragmentById(R.id.drawerSwitch)!!.tag
            )
        }
    }
}