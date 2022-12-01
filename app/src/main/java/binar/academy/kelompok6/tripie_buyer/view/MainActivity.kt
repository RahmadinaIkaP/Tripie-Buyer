package binar.academy.kelompok6.tripie_buyer.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import binar.academy.kelompok6.tripie_buyer.R
import binar.academy.kelompok6.tripie_buyer.databinding.ActivityMainBinding
import binar.academy.kelompok6.tripie_buyer.view.histori.HistoriFragment
import binar.academy.kelompok6.tripie_buyer.view.home.HomeFragment
import binar.academy.kelompok6.tripie_buyer.view.profile.ProfileFragment
import binar.academy.kelompok6.tripie_buyer.view.whistlist.WhistlistFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragmentContainerView,HomeFragment())
            commit()
        }

         binding.bottomNavigationView.setOnItemSelectedListener setOnItemReselectedListener@{
            when(it.itemId){
                R.id.homeFragment ->{
                    supportFragmentManager.beginTransaction().replace(R.id.fragmentContainerView,HomeFragment()).commit()
                    return@setOnItemReselectedListener true
                } R.id.historiFragment ->{
                supportFragmentManager.beginTransaction().replace(R.id.fragmentContainerView,HistoriFragment()).commit()
                return@setOnItemReselectedListener true
                } R.id.whistlistFragment ->{
                    supportFragmentManager.beginTransaction().replace(R.id.fragmentContainerView,WhistlistFragment()).commit()
                    return@setOnItemReselectedListener true
                }R.id.profileFragment ->{
                    supportFragmentManager.beginTransaction().replace(R.id.fragmentContainerView,ProfileFragment()).commit()
                    return@setOnItemReselectedListener true
                }
            }
            false
        }
    }
}