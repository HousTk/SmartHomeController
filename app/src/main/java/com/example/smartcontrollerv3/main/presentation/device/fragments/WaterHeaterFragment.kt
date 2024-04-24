package com.example.smartcontrollerv3.main.presentation.device.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import com.example.domain.domain.models.main.Device
import com.example.smartcontrollerv3.R
import com.example.smartcontrollerv3.databinding.FragmentWaterHeaterBinding


class WaterHeaterFragment(
    private val deviceLD: LiveData<Device>,
    private val callback: Callback
) : Fragment() {

    interface Callback {
        fun targetTempChange(targetTemp: Int)
        fun turnOn()
        fun turnOff()
    }

    private lateinit var binding: FragmentWaterHeaterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentWaterHeaterBinding.inflate(inflater)

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

            fragmentWaterHeaterState.setImageResource(
                if (device.settings.state) R.drawable.ic_power_green
                else R.drawable.ic_power_red
            )

            fragmentWaterHeaterTempTextVal.text = device.settings.targetTemp?.toString()

        }

    }

    private fun initUi() {
        with(binding) {



            fragmentWaterHeaterState.setOnClickListener{
                if(deviceLD.value!!.settings.state) callback.turnOff()
                else callback.turnOn()
            }

            fragmentWaterHeaterTempSendBtn.setOnClickListener {
                callback.targetTempChange(fragmentWaterHeaterTemp.text.toString().toInt())
            }

        }
    }

}