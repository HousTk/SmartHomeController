package com.example.smartcontrollerv3.main.presentation.logIn.logUp

import android.os.Bundle
import android.text.InputType
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.smartcontrollerv3.R
import com.example.smartcontrollerv3.databinding.FragmentLogUpBinding
import com.example.smartcontrollerv3.main.utils.AnyCallback
import com.example.smartcontrollerv3.main.utils.ERROR
import org.koin.androidx.viewmodel.ext.android.viewModel

class LogUpFragment : Fragment() {

    private lateinit var binding: FragmentLogUpBinding

    private val vm by viewModel<LogUpViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentLogUpBinding.inflate(inflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        initUi()

        initVMobserver()

    }
    private fun initUi(){

        with(binding){

            //fragmentLogUpPassword.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD

            fragmentLogUpPassword.inputType = InputType.TYPE_TEXT_VARIATION_PASSWORD

            fragmentLogUpButton.setOnClickListener{

                val email = fragmentLogUpEmail.text.toString()
                val password = fragmentLogUpPassword.text.toString()

                vm.logUp(email = email, password = password)


            }
        }
    }

    private fun initVMobserver(){

        vm.errorMessage.observe(requireActivity()){ message ->

            if(message != null){

                binding.fragmentLogUpPasswordMinSymbols.visibility = View.VISIBLE
                binding.fragmentLogUpPasswordMinSymbols.text = message

            }else{

                binding.fragmentLogUpPasswordMinSymbols.visibility = View.GONE

            }

        }

    }

}