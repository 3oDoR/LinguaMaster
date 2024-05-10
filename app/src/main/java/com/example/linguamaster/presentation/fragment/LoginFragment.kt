package com.example.linguamaster.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import com.example.linguamaster.R
import com.example.linguamaster.databinding.FragmentLoginBinding
import com.example.linguamaster.domain.model.RegistrationFormState
import com.example.linguamaster.presentation.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.runBlocking

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private val vm: LoginViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)

        vm.emailLiveData.observe(viewLifecycleOwner) {
            binding.etEmail.error = it
        }
        vm.passwordLiveData.observe(viewLifecycleOwner) {
            binding.etPassword.error = it
        }
        vm.successLoginLiveData.observe(viewLifecycleOwner) {
            parentFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
            val navBar = requireActivity().findViewById<View>(R.id.bottom_navigation)
            navBar.visibility = View.VISIBLE
            parentFragmentManager.commit {
                replace(R.id.rl, HomeFragment.newInstance(), "HomeFragment")
            }
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnSignIn.setOnClickListener {
            runBlocking {
                vm.submitData(
                    RegistrationFormState(
                        email = binding.etEmail.text.toString(),
                        password = binding.etPassword.text.toString()
                    )
                )
            }
        }

        binding.tvSignUp.setOnClickListener {
            parentFragmentManager.commit {
                setCustomAnimations(
                    R.anim.slide_in_top,
                    R.anim.slide_out_bottom,
                    R.anim.slide_in_bottom,
                    R.anim.slide_out_top
                )
                replace(R.id.rl, RegisterFragment.newInstance())
                addToBackStack(null)
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = LoginFragment()
    }
}