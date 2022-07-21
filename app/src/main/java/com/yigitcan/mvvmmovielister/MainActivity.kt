package com.yigitcan.mvvmmovielister

import android.os.Bundle
import android.util.Log
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.yigitcan.mvvmmovielister.databinding.ActivityMainBinding
import com.yigitcan.mvvmmovielister.ui.details.DetailsFragment
import com.yigitcan.mvvmmovielister.ui.list.ListFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val fragmentList = ListFragment()
        val fragmentDetails = DetailsFragment()
        replaceCurrentFragment(fragmentList)
        title = navView.menu.findItem(navView.selectedItemId).title

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main) as NavHostFragment
        val navController = navHostFragment.navController
        navView.setupWithNavController(navController)




        navView.setOnItemSelectedListener { item ->
            when(item.itemId) {
                R.id.navigation_list -> {
                    Log.i("NavBar","List pressed")
                    replaceCurrentFragment(fragmentList)
                    title = navView.menu.findItem(item.itemId).title
                    true
                }
                R.id.navigation_details -> {
                    Log.i("NavBar","Details pressed")
                    replaceCurrentFragment(fragmentDetails)
                    title = navView.menu.findItem(item.itemId).title
                    true
                }
                else -> {
                    Log.i("NavBar","Error?")
                    false
                }
            }
        }
    }

    private fun replaceCurrentFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.nav_host_fragment_activity_main, fragment)
            addToBackStack(null)
            commit()
        }
}