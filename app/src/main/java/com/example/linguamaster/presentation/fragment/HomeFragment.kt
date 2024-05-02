package com.example.linguamaster.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.example.linguamaster.R
import com.example.linguamaster.databinding.FragmentHomeBinding
import com.google.firebase.auth.FirebaseAuth

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        binding.tvDailyWord.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            parentFragmentManager.commit {
                replace(R.id.rl, LoginFragment.newInstance(), "LoginFragment")
            }
        }
        return binding.root
    }

    override fun onStop() {
        super.onStop()
        val navBar = requireActivity().findViewById<View>(R.id.bottom_navigation)
        navBar.visibility = View.GONE
    }

    companion object {
        @JvmStatic
        fun newInstance() = HomeFragment()
    }
}