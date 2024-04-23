package com.example.smartcontrollerv3.main.presentation.addDeviceToRoom

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.smartcontrollerv3.R
import com.example.smartcontrollerv3.databinding.FragmentAddDeviceToRoomFirstPageBinding
import org.koin.androidx.viewmodel.ext.android.viewModel


class AddDeviceToRoomFirstPageFragment : Fragment() {

    private lateinit var binding: FragmentAddDeviceToRoomFirstPageBinding

    private val vm by viewModel<AddDeviceToRoomFirstPageViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentAddDeviceToRoomFirstPageBinding.inflate(inflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initUi()
    }

    private fun initUi(){
        with(binding){
            fragmentAddDeviceToRoomFirstPageAddDeviceButton.setOnClickListener{
                vm.onClickAddDevice(arguments?.getInt("CurrentRoom") ?: -1)
            }

            fragmentAddDeviceToRoomFirstPageAddNewDeviceButton.setOnClickListener{
                vm.onClickAddNewDevice(arguments?.getInt("CurrentRoom") ?: -1)
            }
        }

    }
}