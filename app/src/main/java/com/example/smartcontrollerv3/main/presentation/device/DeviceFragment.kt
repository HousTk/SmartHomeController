package com.example.smartcontrollerv3.main.presentation.device

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.domain.domain.models.main.Device
import com.example.domain.domain.utils.TYPE_LEDCONTROLLER
import com.example.domain.domain.utils.TYPE_LIGHTCONTROLLER
import com.example.domain.domain.utils.TYPE_MOVE_LIGHTCONTROLLER
import com.example.domain.domain.utils.TYPE_SWITCH_LIGHTCONTROLLER
import com.example.domain.domain.utils.TYPE_SWITCH_MOVE_LIGHTCONTROLLER
import com.example.domain.domain.utils.TYPE_WATERHEATER
import com.example.smartcontrollerv3.databinding.FragmentDeviceBinding
import com.example.smartcontrollerv3.main.presentation.device.fragments.LedControllerFragment
import com.example.smartcontrollerv3.main.presentation.device.fragments.LightControllerFragment
import com.example.smartcontrollerv3.main.presentation.device.fragments.WaterHeaterFragment
import com.example.smartcontrollerv3.main.utils.getDeviceIconByType
import com.example.smartcontrollerv3.main.utils.getDeviceNameByType
import org.koin.androidx.viewmodel.ext.android.viewModel


class DeviceFragment : Fragment() {

    val KEY_ROOM_ID = "RoomId"
    val KEY_DEVICE_ID = "DeviceId"

    private lateinit var binding: FragmentDeviceBinding

    private val vm by viewModel<DeviceViewModel>()

    private var isInit:Boolean = false

    private lateinit var fragment:Fragment

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentDeviceBinding.inflate(inflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        initVMobservers()
        getArgs()

        vm.init(lifecycleOwner = requireActivity())
    }

    private fun getArgs() {

        val roomId = arguments?.getLong(KEY_ROOM_ID)

        val deviceId = arguments?.getLong(KEY_DEVICE_ID)

        vm.applyArgs(deviceId = deviceId, roomId = roomId)

    }

    private fun initVMobservers(){

        vm.device.observe(requireActivity()){device ->

            if(!isInit) {
                initUi(device)
            }

            updateUi(device)

        }

    }

    private fun updateUi(device: Device){

        with(binding){

            fragmentDeviceToolbar.toolbarDeviceText.text = device.name

            fragmentDeviceTextName.text = device.name
            fragmentDeviceIP.text = device.ip
            fragmentDeviceType.text = getDeviceNameByType(deviceType = device.type)
            fragmentDeviceStatus.text = if (device.status) "Online" else "Offline"

            fragmentDeviceImage.setImageResource(getDeviceIconByType(device.type))


        }



    }

    private fun initUi(device:Device) {
        with(binding) {
            with(fragmentDeviceToolbar) {

                toolbarDeviceSettings.setOnClickListener {
                    onClickSettings(it)
                }
                toolbarDeviceBack.setOnClickListener {
                    onClickBack()
                }

            }

            when(device.type){

                TYPE_LEDCONTROLLER -> {
                    fragment = LedControllerFragment(
                        deviceLD = vm.device,
                        callback = object : LedControllerFragment.Callback{

                            override fun onBrightnessChange(brightness: Int) {
                                vm.changeBrightness(brightness)
                            }

                            override fun onColorChange(red: Int, green: Int, blue: Int) {
                                vm.changeColor(red, green, blue)
                            }

                            override fun turnOn() {
                                vm.turnOn()
                            }

                            override fun turnOff() {
                                vm.turnOff()
                            }

                        }
                    )

                }


                TYPE_WATERHEATER ->{
                    fragment = WaterHeaterFragment(
                        deviceLD = vm.device,
                        callback = object : WaterHeaterFragment.Callback{
                            override fun targetTempChange(targetTemp: Int) {
                                vm.changeTargetTemp(targetTemp)
                            }

                            override fun turnOn() {
                                vm.turnOn()
                            }

                            override fun turnOff() {
                                vm.turnOff()
                            }

                        }
                    )
                }


               TYPE_SWITCH_MOVE_LIGHTCONTROLLER, TYPE_MOVE_LIGHTCONTROLLER, TYPE_SWITCH_LIGHTCONTROLLER, TYPE_LIGHTCONTROLLER ->{
                   fragment = LightControllerFragment(
                       deviceLD = vm.device,
                       callback = object : LightControllerFragment.Callback{
                           override fun movementTimeoutChange(timeout: Int) {
                               vm.changeMoveTimeout(timeout)
                           }

                           override fun turnOn() {
                               vm.turnOn()
                           }

                           override fun turnOff() {
                               vm.turnOff()
                           }

                       }
                   )
                }

            }

            if(this@DeviceFragment::fragment.isInitialized) {

                val manager = childFragmentManager.beginTransaction()

                manager.replace(fragmentDeviceFC.id, fragment)

                manager.commit()
            }

            isInit = true
        }





    }


    private fun onClickSettings(view: View) {
        vm.onClickSettings(view)
    }

    private fun onClickBack() {
        vm.onClickBack()
    }
}