package com.example.smartcontrollerv3.main.presentation.logIn.logIn

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.smartcontrollerv3.R
import com.example.smartcontrollerv3.databinding.FragmentLogInBinding
import com.example.smartcontrollerv3.main.utils.AnyCallback
import org.koin.androidx.viewmodel.ext.android.viewModel

class LogInFragment : Fragment() {

    private lateinit var binding: FragmentLogInBinding

    private val vm by viewModel<LogInViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentLogInBinding.inflate(inflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        initUi()

        initVMobserver()

    }

    private fun initUi(){

        with(binding){

            fragmentLogInButton.setOnClickListener{
                onLogInClick()
            }

        }

    }

    private fun onLogInClick(){


        val email = binding.fragmentLogInEmail.text.toString()
        val password = binding.fragmentLogInPassword.text.toString()

        vm.logIn(
            email = email,
            password = password,
            )

    }

    private fun initVMobserver(){

        vm.errorMessage.observe(requireActivity()){message ->

            if(message != null){

                binding.fragmentLogInErrorMessage.visibility = View.VISIBLE
                binding.fragmentLogInErrorMessage.text = message

            }else{

                binding.fragmentLogInErrorMessage.visibility = View.GONE

            }

        }

    }


}