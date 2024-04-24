package com.example.smartcontrollerv3.main.presentation.addresses

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.domain.models.main.Device
import com.example.smartcontrollerv3.databinding.ItemFragmentAddressesDeviceBinding
import com.example.smartcontrollerv3.main.utils.getDeviceIconByType
import com.example.smartcontrollerv3.main.utils.getDeviceNameByType

class AddressesDevicesAdapter(): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var devicesList = emptyList<Device>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val binding = ItemFragmentAddressesDeviceBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return DeviceHolder(binding)
    }

    override fun getItemCount(): Int {
        return devicesList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        (holder as DeviceHolder).bind(position)

    }

    private inner class DeviceHolder(private val binding: ItemFragmentAddressesDeviceBinding) :
        RecyclerView.ViewHolder(binding.root) {

            fun bind(position: Int){

                val device = devicesList[position]

                with(binding){

                    itemFragmentAddressesDeviceImage.setImageResource(getDeviceIconByType(device.type))
                    itemFragmentAddressesDeviceName.text = device.name
                    itemFragmentAddressesDeviceType.text = getDeviceNameByType(device.type)
                    itemFragmentAddressesDeviceIP.text = device.ip

                }
            }

    }

    fun updateDevicesList(devices: List<Device>){
        devicesList = devices
        notifyDataSetChanged()
    }
}