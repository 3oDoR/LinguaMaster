package com.example.linguamaster.presentation

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.example.linguamaster.R
import com.example.linguamaster.databinding.ActivityMainBinding
import com.example.linguamaster.presentation.fragment.HomeFragment
import com.example.linguamaster.presentation.fragment.WelcomeFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

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
}