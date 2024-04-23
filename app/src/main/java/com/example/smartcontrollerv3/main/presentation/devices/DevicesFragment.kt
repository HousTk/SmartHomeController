package com.example.smartcontrollerv3.main.presentation.devices

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.domain.domain.models.device.Device
import com.example.smartcontrollerv3.databinding.FragmentDevicesBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class DevicesFragment : Fragment() {


    private lateinit var binding: FragmentDevicesBinding
    private lateinit var devicesAdapter: DevicesFragmentAdapter

    private val vm by viewModel<DevicesViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentDevicesBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        initUi()

        vm.updateDevicesList()

        vm.devicesList.observe(requireActivity(), Observer {
            updateUi(it)
        })

    }

    override fun onDestroyView() {
        super.onDestroyView()
    }


    private fun initUi() {

        devicesAdapter = DevicesFragmentAdapter(
            object : DevicesFragmentInterface{
                override fun deleteDevice(deviceId: Int) {
                    vm.delete(deviceId = deviceId)
                }

            }
        )

        with(binding) {

            with(toolbarDevices){
                toolbarDevicesBack.setOnClickListener{
                    vm.onClickBack()
                }

                toolbarDevicesAdd.setOnClickListener{
                    vm.onClickAddDevice()
                }

                toolbarDevicesMore.setOnClickListener{
                    vm.onClickMore(it)
                }

                toolbarDevicesText.text = "Devices"
            }


            fragmentDevicesRV.layoutManager = LinearLayoutManager(
                fragmentDevicesRV.context,
                LinearLayoutManager.VERTICAL,
                false
            )

            fragmentDevicesRV.adapter = devicesAdapter

        }
    }


    private fun updateUi(deviceList: ArrayList<Device>) {

        devicesAdapter.updateDevices(deviceList)

    }
}
