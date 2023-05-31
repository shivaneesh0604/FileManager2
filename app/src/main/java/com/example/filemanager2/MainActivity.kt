package com.example.filemanager2

import android.content.DialogInterface
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.activityViewModels
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var drawerLayout: DrawerLayout
    private val homeFragment: HomeFragment = HomeFragment()
    private val aboutUsActivity: AboutUsActivity = AboutUsActivity()
    var activeFragmentTag: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        drawerLayout = findViewById(R.id.drawerLayout)
        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.start, R.string.end)

        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val navigationView = findViewById<NavigationView>(R.id.navigation_view)
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
                replace(R.id.drawerSwitch, homeFragment)
                commit()
            }
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.home ->
                supportFragmentManager.beginTransaction().replace(R.id.drawerSwitch, homeFragment)
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

//    override fun onBackPressed() {
//        // Build the confirmation dialog
//
//        if (supportFragmentManager.backStackEntryCount == 0) {
//            val builder = AlertDialog.Builder(this)
//            builder.setTitle("Exit App")
//            builder.setMessage("Are you sure you want to exit?")
//
//            // Set positive button click listener
//            builder.setPositiveButton("Yes") { dialogInterface: DialogInterface, _: Int ->
//                // Finish the activity and exit the app
//                finish()
//            }
//
//            // Set negative button click listener
//            builder.setNegativeButton("No") { dialogInterface: DialogInterface, _: Int ->
//                // Dismiss the dialog and continue with the back operation
//                dialogInterface.dismiss()
//            }
//
//            // Show the confirmation dialog
//            val dialog = builder.create()
//            dialog.show()
//        }
//    }

//    override fun onBackPressed() {
//        super.onBackPressed()
//        Log.i("back", "called on back pressed")
//
////        if (activeFragmentTag == "DocxFragment") {
////            Log.e("back","called docx")
////            val docxFragmentViewModel: DocxFragmentViewModel by viewModels()
////            docxFragmentViewModel.file = null
////        } else if (activeFragmentTag == "DocFragment") {
////            Log.e("back","called doc")
////            val docFragmentViewModel: DocFragmentViewModel by viewModels()
////            docFragmentViewModel.file = null
////        } else if (activeFragmentTag == "TextFragment") {
////            Log.e("back","called text")
////            val textFragmentViewModel: TextFragmentViewModel by viewModels()
////            textFragmentViewModel.file = null
////        } else if (activeFragmentTag == "AllFragment") {
////            Log.e("back","called all")
////            val allFragmentViewModel: AllFragmentViewModel by viewModels()
////            allFragmentViewModel.file = null
////        }
//    }
}