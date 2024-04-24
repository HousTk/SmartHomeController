package com.example.smartcontrollerv3.main.presentation.device.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import com.example.domain.domain.models.main.Device
import com.example.domain.domain.utils.TYPE_LIGHTCONTROLLER
import com.example.domain.domain.utils.TYPE_MOVE_LIGHTCONTROLLER
import com.example.domain.domain.utils.TYPE_SWITCH_LIGHTCONTROLLER
import com.example.domain.domain.utils.TYPE_SWITCH_MOVE_LIGHTCONTROLLER
import com.example.smartcontrollerv3.R
import com.example.smartcontrollerv3.databinding.FragmentLightControllerBinding


class LightControllerFragment(
    private val deviceLD: LiveData<Device>,
    private val callback: Callback
) : Fragment() {

    interface Callback {
        fun movementTimeoutChange(timeout: Int)
        fun turnOn()
        fun turnOff()
    }

    private lateinit var binding: FragmentLightControllerBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLightControllerBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        initUi()

        updateUi(deviceLD.value!!)

        initObserver()
    }

    private fun initObserver(){
        deviceLD.observe(requireActivity()){device->

            updateUi(device)

        }
    }



    private fun updateUi(device: Device){

        with(binding) {

            fragmentLightControllerState.setImageResource(
                if (device.settings.state) R.drawable.ic_power_green
                else R.drawable.ic_power_red
            )

            fragmentLightControllerTimeoutVal.text = device.settings.targetTemp?.toString()

        }

    }

    private fun initUi() {

        with(binding) {

            fragmentLightControllerState.setOnClickListener{
                if(deviceLD.value!!.settings.state) callback.turnOff()
                else callback.turnOn()
            }

        }

        when(deviceLD.value!!.type){

            TYPE_LIGHTCONTROLLER ->{
                initLight()
            }

            TYPE_SWITCH_LIGHTCONTROLLER ->{
                initSwitchLight()
            }

            TYPE_MOVE_LIGHTCONTROLLER ->{
                initMoveLight()
            }

            TYPE_SWITCH_MOVE_LIGHTCONTROLLER ->{
                initSwitchLight()
                initMoveLight()
            }

        }
    }

    private fun initLight(){

    }

    private fun initSwitchLight(){

    }

    private fun initMoveLight(){

        with(binding) {

            fragmentLightControllerMove.visibility = View.VISIBLE

            fragmentLightControllerSendBtn.setOnClickListener {
                callback.movementTimeoutChange(
                    fragmentLightControllerTimeout.text.toString().toInt()
                )
            }

        }
    }


}