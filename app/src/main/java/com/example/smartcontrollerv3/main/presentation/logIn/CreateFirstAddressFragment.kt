package com.example.smartcontrollerv3.main.presentation.logIn

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModel
import com.example.smartcontrollerv3.R
import com.example.smartcontrollerv3.databinding.FragmentCreateFirstAddressBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class CreateFirstAddressFragment : Fragment() {

    private lateinit var binding: FragmentCreateFirstAddressBinding

    private val vm by viewModel<CreateFirstAddressViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentCreateFirstAddressBinding.inflate(inflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        initUi()

    }

    private fun initUi(){
        with(binding){

            fragmentCreateFirstAddressSave.setOnClickListener{
                val name = fragmentCreateFirstAddressName.text.toString()
                val ssid = fragmentCreateFirstAddressSSID.text.toString()

                vm.onSave(name = name, ssid = ssid)

            }

            fragmentCreateFirstAddressSSIDCheckBox.setOnClickListener{

            }
        }
    }

}