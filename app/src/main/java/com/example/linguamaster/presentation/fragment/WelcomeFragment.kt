package com.example.linguamaster.presentation.fragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.example.linguamaster.R
import com.example.linguamaster.databinding.FragmentWelcomeBinding

class WelcomeFragment : Fragment() {
    private lateinit var welcomeFragmentBinding: FragmentWelcomeBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        welcomeFragmentBinding = FragmentWelcomeBinding.inflate(inflater, container, false)
        return welcomeFragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        welcomeFragmentBinding.btnGetStarted.setOnClickListener {
           parentFragmentManager.commit {
               setCustomAnimations(
                   R.anim.slide_in_left,
                   R.anim.slide_out_right,
                   R.anim.slide_in_right,
                   R.anim.slide_out_left
               )
               replace(R.id.rl, LoginFragment.newInstance(), "LoginFragment")
               addToBackStack(null)
           }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = WelcomeFragment()
    }
}