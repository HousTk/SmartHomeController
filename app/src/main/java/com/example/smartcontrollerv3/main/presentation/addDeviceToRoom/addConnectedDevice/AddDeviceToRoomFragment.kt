package com.example.smartcontrollerv3.main.presentation.addDeviceToRoom.addConnectedDevice

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.example.smartcontrollerv3.databinding.FragmentAddDeviceToRoomBinding
import org.koin.androidx.viewmodel.ext.android.viewModel


class AddDeviceToRoomFragment : Fragment() {

    private lateinit var binding: FragmentAddDeviceToRoomBinding

    private lateinit var devicesAdapter: AddDeviceToRoomDevicesAdapter

    private val vm by viewModel<AddDeviceToRoomViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentAddDeviceToRoomBinding.inflate(inflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        initVm()

        initAdapters()
        initUi()
        initVMobservers()

    }

    private fun initVm(){
        vm.initVM(arguments?.getLong("CurrentRoom") ?: -1)
    }
    private fun initAdapters(){

        devicesAdapter = AddDeviceToRoomDevicesAdapter(
            object : AddDeviceToRoomDevicesAdapterInterface{
                override fun onSelectDevice(deviceId: Long) {
                    vm.selectDevice(deviceId)
                }
            }
        )

    }
    private fun initUi() {

        with(binding) {

            with(fragmentAddDeviceToRoomToolbar) {

                toolbarConfirmationBack.setOnClickListener {

                }

                toolbarConfirmationText.text = "Add device"

                toolbarConfirmationSave.visibility = View.GONE

                toolbarConfirmationSave.setOnClickListener {

                    vm.save()

                }

            }

            fragmentAddDeviceToRoomRV.layoutManager=GridLayoutManager(
                context,
                2
            )

            fragmentAddDeviceToRoomRV.adapter=devicesAdapter

        }

    }

    private fun initVMobservers() {

        vm.selectedDevices.observe(requireActivity()) { selectedDevices ->

            updateUi(selectedDevices)

        }

        vm.devicesList.observe(requireActivity()){ list ->

            devicesAdapter.setDevicesList(list)

        }

    }

    private fun updateUi(selectedDevices: List<Long>) {

        if (selectedDevices.isNotEmpty()) {
            binding.fragmentAddDeviceToRoomToolbar.toolbarConfirmationSave.visibility = View.VISIBLE
        }else{
            binding.fragmentAddDeviceToRoomToolbar.toolbarConfirmationSave.visibility = View.GONE
        }

        devicesAdapter.updateSelectedDevicesIds(selectedDevicesIds = selectedDevices)
    }
}