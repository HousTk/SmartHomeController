package com.example.smartcontrollerv3.main.presentation.device.fragments

import android.graphics.Color
import android.os.Bundle

import android.view.LayoutInflater

import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.core.graphics.blue
import androidx.core.graphics.green
import androidx.core.graphics.red
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData

import com.example.domain.domain.models.main.Device
import com.example.smartcontrollerv3.R
import com.example.smartcontrollerv3.databinding.FragmentLedControllerBinding
import com.example.smartcontrollerv3.main.utils.makeLogI
import com.skydoves.colorpickerview.ActionMode

import com.skydoves.colorpickerview.listeners.ColorListener


class LedControllerFragment(
    private val callback: Callback,
    private val deviceLD: LiveData<Device>
) : Fragment() {

    interface Callback {

        fun onBrightnessChange(brightness: Int)

        fun onColorChange(red: Int, green: Int, blue: Int)

        fun turnOn()

        fun turnOff()

    }

    private lateinit var binding: FragmentLedControllerBinding

    private var rgbState: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentLedControllerBinding.inflate(inflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initUi()

        updateUi(deviceLD.value!!)

        initObserver()
    }

    fun initObserver(){
        deviceLD.observe(requireActivity()){device ->

            updateUi(device)

        }
    }


    private fun updateUi(device:Device){

        with(binding) {

            fragmentLedControllerState.setImageResource(
                if (device.settings.state) R.drawable.ic_power_green
                else R.drawable.ic_power_red
            )

            fragmentLedControllerBrightness.progress = device.settings.brightness!!

            fragmentLedControllerRed.progress = device.settings.red!!
            fragmentLedControllerGreen.progress = device.settings.green!!
            fragmentLedControllerBlue.progress = device.settings.blue!!


        }
    }

    private fun navigateBackWithError(){

    }
    private fun initUi() {

        with(binding) {



            fragmentLedControllerRGBswitch.setOnClickListener {

                rgbState = !rgbState

                changeColorPicker()

            }


            fragmentLedControllerState.setOnClickListener{
                if(deviceLD.value!!.settings.state) callback.turnOff()
                else callback.turnOn()
            }



            fragmentLedControllerBrightness.setOnSeekBarChangeListener(
                object : SeekBar.OnSeekBarChangeListener {

                    override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                        fragmentLedControllerBrightnessTextValue.text = p0?.progress.toString()
                    }

                    override fun onStartTrackingTouch(p0: SeekBar?) {
                        fragmentLedControllerBrightnessTextValue.visibility = View.VISIBLE
                    }

                    override fun onStopTrackingTouch(p0: SeekBar?) {
                        fragmentLedControllerBrightnessTextValue.visibility = View.GONE

                        callback.onBrightnessChange(brightness = p0?.progress ?: 0)
                    }

                }
            )

            fragmentLedControllerRed.max = 255
            fragmentLedControllerRed.setOnSeekBarChangeListener(
                object : SeekBar.OnSeekBarChangeListener {
                    override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {

                    }

                    override fun onStartTrackingTouch(p0: SeekBar?) {

                    }

                    override fun onStopTrackingTouch(p0: SeekBar?) {
                        updateColor()
                    }

                }
            )

            fragmentLedControllerGreen.max = 255
            fragmentLedControllerGreen.setOnSeekBarChangeListener(
                object : SeekBar.OnSeekBarChangeListener {
                    override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {

                    }

                    override fun onStartTrackingTouch(p0: SeekBar?) {

                    }

                    override fun onStopTrackingTouch(p0: SeekBar?) {
                        updateColor()
                    }

                }
            )

            fragmentLedControllerBlue.max = 255
            fragmentLedControllerBlue.setOnSeekBarChangeListener(
                object : SeekBar.OnSeekBarChangeListener {
                    override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {

                    }

                    override fun onStartTrackingTouch(p0: SeekBar?) {

                    }

                    override fun onStopTrackingTouch(p0: SeekBar?) {
                        updateColor()
                    }

                }
            )





            fragmentLedControllerColorPicker.setActionMode(ActionMode.LAST);
            fragmentLedControllerColorPicker.setColorListener(
                object : ColorListener {


                    override fun onColorSelected(color: Int, fromUser: Boolean) {
                        updateColor()
                    }

                }
            )

        }

    }

    private fun updateColor() {
        if (!rgbState) {
            callback.onColorChange(
                red = binding.fragmentLedControllerRed.progress,
                green = binding.fragmentLedControllerGreen.progress,
                blue = binding.fragmentLedControllerBlue.progress
            )
        } else {
            callback.onColorChange(
                red = binding.fragmentLedControllerColorPicker.color.red,
                green = binding.fragmentLedControllerColorPicker.color.green,
                blue = binding.fragmentLedControllerColorPicker.color.blue
            )

//            makeLogI("${binding.fragmentLedControllerColorPicker.color.red}")
//            makeLogI("${binding.fragmentLedControllerColorPicker.color.green}")
//            makeLogI("${binding.fragmentLedControllerColorPicker.color.blue}")

        }
    }

    private fun changeColorPicker() {
        if (!rgbState) {

            with(binding) {

                fragmentLedControllerRedText.visibility = View.VISIBLE
                fragmentLedControllerRed.visibility = View.VISIBLE
                fragmentLedControllerGreenText.visibility = View.VISIBLE
                fragmentLedControllerGreen.visibility = View.VISIBLE
                fragmentLedControllerBlueText.visibility = View.VISIBLE
                fragmentLedControllerBlue.visibility = View.VISIBLE
                fragmentLedControllerColorPicker.visibility = View.GONE
                fragmentLedControllerRGBswitch.text = "ColorPicker"

            }

        } else {

            with(binding) {

                fragmentLedControllerRedText.visibility = View.GONE
                fragmentLedControllerRed.visibility = View.GONE
                fragmentLedControllerGreenText.visibility = View.GONE
                fragmentLedControllerGreen.visibility = View.GONE
                fragmentLedControllerBlueText.visibility = View.GONE
                fragmentLedControllerBlue.visibility = View.GONE
                fragmentLedControllerColorPicker.visibility = View.VISIBLE
                fragmentLedControllerRGBswitch.text = "RGB"

            }
        }
    }
}