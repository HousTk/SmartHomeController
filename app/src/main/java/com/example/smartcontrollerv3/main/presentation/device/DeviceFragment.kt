package com.example.smartcontrollerv3.main.presentation.device

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.smartcontrollerv3.databinding.FragmentDeviceBinding
import com.example.smartcontrollerv3.main.utils.getDeviceIconByType
import com.example.smartcontrollerv3.main.utils.getDeviceNameByType
import org.koin.androidx.viewmodel.ext.android.viewModel


class DeviceFragment : Fragment() {

    val KEY_ROOM_POSITION = "RoomPosition"
    val KEY_DEVICE_ID = "DeviceId"

    private lateinit var binding: FragmentDeviceBinding

    private val vm by viewModel<DeviceViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentDeviceBinding.inflate(inflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        getArgs()

        initUi()

    }

    private fun getArgs() {

        val roomPosition = arguments?.getInt(KEY_ROOM_POSITION)

        val deviceId = arguments?.getInt(KEY_DEVICE_ID)

        vm.applyArgs(deviceId = deviceId, roomPosition = roomPosition)

    }

    private fun initUi() {
        with(binding) {
            with(fragmentDeviceToolbar) {
                toolbarStockText.text = vm.device.name
                toolbarStockMore.setOnClickListener {
                    onClickMore(it)
                }
                toolbarStockBack.setOnClickListener {
                    onClickBack()
                }
            }

            fragmentDeviceTextName.text = vm.device.name
            fragmentDeviceIP.text = vm.device.ip
            fragmentDeviceType.text = getDeviceNameByType(deviceType = vm.device.type)
            fragmentDeviceStatus.text = if (vm.device.status) "Online" else "Offline"

            fragmentDeviceImage.setImageResource(getDeviceIconByType(vm.device.type))
        }
    }


    private fun onClickMore(view: View) {
        vm.onClickMore(view)
    }

    private fun onClickBack() {
        vm.onClickBack()
    }
}