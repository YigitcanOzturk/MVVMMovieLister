package com.yigitcan.mvvmmovielister

import android.os.Bundle
import android.util.Log
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.yigitcan.mvvmmovielister.databinding.ActivityMainBinding
import com.yigitcan.mvvmmovielister.ui.details.DetailsFragment
import com.yigitcan.mvvmmovielister.ui.favorites.FavoritesFragment
import com.yigitcan.mvvmmovielister.ui.list.ListFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val fragmentList = ListFragment()
        val fragmentFavorites = FavoritesFragment()
        val fragmentDetails = DetailsFragment()
        replaceCurrentFragment(fragmentList)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main) as NavHostFragment
        val navController = navHostFragment.navController
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_list, R.id.navigation_favorites, R.id.navigation_details
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        binding.navView.setOnItemSelectedListener { item ->
            when(item.itemId) {
                R.id.navigation_list -> {
                    Log.i("NavBar","List pressed")
                    replaceCurrentFragment(fragmentList)
                    true
                }
                R.id.navigation_favorites -> {
                    Log.i("NavBar","Favorites pressed")
                    replaceCurrentFragment(fragmentFavorites)
                    true
                }
                R.id.navigation_details -> {
                    Log.i("NavBar","Details pressed")
                    replaceCurrentFragment(fragmentDetails)
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