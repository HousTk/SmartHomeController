package com.example.smartcontrollerv3.main.presentation.addDeviceToRoom.addConnectedDevice

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.domain.models.main.Device
import com.example.smartcontrollerv3.databinding.ItemFragmentAddDeviceToRoomDeviceBinding
import com.example.smartcontrollerv3.main.utils.getDeviceIconByType
import com.example.smartcontrollerv3.main.utils.getDeviceNameByType

class AddDeviceToRoomDevicesAdapter(
    private val callback: AddDeviceToRoomDevicesAdapterInterface,
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

        private var devicesList = emptyList<Device>()

    private var selectedDevices = ArrayList<Long>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ItemFragmentAddDeviceToRoomDeviceBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return DeviceHolder(binding)
    }

    override fun getItemCount(): Int {
        return devicesList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        (holder as DeviceHolder).bind(position)

    }

    private inner class DeviceHolder(private val binding: ItemFragmentAddDeviceToRoomDeviceBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(position: Int) {

            val device = devicesList[position]

            with(binding) {
                itemFragmentAddDeviceToRoomDeviceImage.setImageResource(getDeviceIconByType(device.type))
                itemFragmentAddDeviceToRoomDeviceIP.text = device.ip
                itemFragmentAddDeviceToRoomDeviceName.text = device.name
                itemFragmentAddDeviceToRoomDeviceType.text = getDeviceNameByType(device.type)

                itemFragmentAddDeviceToRoomDevice.setOnClickListener {
                    callback.onSelectDevice(device.id)
                }

                itemFragmentAddDeviceToRoomDeviceCheckBox.setOnClickListener {
                    callback.onSelectDevice(device.id)
                }

                itemFragmentAddDeviceToRoomDeviceCheckBox.isChecked =
                    selectedDevices.contains(device.id)

            }

        }

    }

    fun setDevicesList(list: List<Device>){

        devicesList = list

        notifyDataSetChanged()

    }

    fun updateSelectedDevicesIds(selectedDevicesIds: List<Long>) {
        selectedDevices = ArrayList(selectedDevicesIds)

        notifyDataSetChanged()
    }

}