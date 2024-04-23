package com.example.smartcontrollerv3.main.presentation.addDevice

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import com.example.smartcontrollerv3.R
import com.example.smartcontrollerv3.databinding.FragmentAddNewDeviceBinding
import org.koin.androidx.viewmodel.ext.android.viewModel


class AddNewDeviceFragment : Fragment() {
    private lateinit var binding: FragmentAddNewDeviceBinding

    private val vm by viewModel<AddDeviceViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requireActivity().onBackPressedDispatcher.addCallback(this) {
            onClickBack()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentAddNewDeviceBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        initUi()

    }

    private fun initUi() {
        with(binding){
            fragmentAddDeviceSave.setOnClickListener {
                onClickSave()
            }

            fragmentAddDeviceToolbar.toolbarStockBack.setOnClickListener{
                vm.back()
            }

            fragmentAddDeviceToolbar.toolbarStockMore.setOnClickListener{
                vm.onClickMore(it)
            }

            fragmentAddDeviceToolbar.toolbarStockText.text = "New device"
        }
    }

    private fun onClickSave(){

        val name = binding.fragmentAddDeviceName.text.toString()
        val ip = binding.fragmentAddDeviceIp.text.toString()
        val currentRoom = arguments?.getInt("CurrentRoom") ?: -1

        vm.save(name = name, ip = ip, currentRoom = currentRoom)
    }

    private fun onClickBack(){

        vm.back()

    }
}