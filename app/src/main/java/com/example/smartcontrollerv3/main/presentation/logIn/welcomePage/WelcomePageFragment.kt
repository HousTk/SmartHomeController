package com.example.smartcontrollerv3.main.presentation.logIn.welcomePage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.smartcontrollerv3.R
import com.example.smartcontrollerv3.databinding.FragmentWelcomePageBinding
import org.koin.androidx.viewmodel.ext.android.viewModel


class WelcomePageFragment : Fragment() {

    private lateinit var binding: FragmentWelcomePageBinding

    private val vm by viewModel<WelcomePageViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentWelcomePageBinding.inflate(inflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initUi()
    }

    private fun initUi(){
        with(binding){
            fragmentWelcomePageSignInButton.setOnClickListener{
                vm.signIn()
            }

            fragmentWelcomePageSignUpButton.setOnClickListener{
                vm.signUp()
            }
        }
    }
}