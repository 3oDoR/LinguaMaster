package com.example.linguamaster.presentation

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.example.linguamaster.R
import com.example.linguamaster.databinding.ActivityMainBinding
import com.example.linguamaster.presentation.fragment.AddFragment
import com.example.linguamaster.presentation.fragment.HomeFragment
import com.example.linguamaster.presentation.fragment.ProfileFragment
import com.example.linguamaster.presentation.fragment.SearchFragment
import com.example.linguamaster.presentation.fragment.WelcomeFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var bindingMainActivity: ActivityMainBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingMainActivity = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bindingMainActivity.root)
        auth = Firebase.auth
    }

    public override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if (currentUser != null) {
            supportFragmentManager.commit {
                bindingMainActivity.bottomNavigation.visibility = View.VISIBLE
                replace(bindingMainActivity.rl.id, HomeFragment.newInstance(), "HomeFragment")
            }
            bindingMainActivity.bottomNavigation.setOnItemSelectedListener {
                when(it.itemId) {
                    R.id.ic_home -> setCurrentFragment(HomeFragment.newInstance())
                    R.id.ic_search -> setCurrentFragment(SearchFragment.newInstance())
                    R.id.ic_add -> setCurrentFragment(AddFragment.newInstance())
                    R.id.ic_profile -> setCurrentFragment(ProfileFragment.newInstance())
                }
                true
            }
        } else {
            supportFragmentManager.commit {
                setCustomAnimations(
                    R.anim.slide_in_right,
                    R.anim.slide_out_left,
                    R.anim.slide_in_left,
                    R.anim.slide_out_right
                )
                replace(bindingMainActivity.rl.id, WelcomeFragment.newInstance(), "WelcomeFragment")
                addToBackStack(null)
            }
        }
    }
    private fun setCurrentFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(bindingMainActivity.rl.id,fragment)
            commit()
        }
}