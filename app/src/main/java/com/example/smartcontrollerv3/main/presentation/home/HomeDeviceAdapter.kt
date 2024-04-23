package com.example.smartcontrollerv3.main.presentation.home

import android.graphics.Color
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.domain.models.device.Device
import com.example.domain.domain.utils.TYPES_ARRAY
import com.example.domain.domain.utils.TYPE_LEDCONTROLLER
import com.example.domain.domain.utils.TYPE_LIGHTCONTROLLER
import com.example.domain.domain.utils.TYPE_WATERCONTROLLER
import com.example.smartcontrollerv3.R
import com.example.smartcontrollerv3.databinding.ItemHomeDeviceBinding
import com.example.smartcontrollerv3.main.utils.getDeviceIconByType

class HomeDeviceAdapter(
    private val callback: HomeDeviceAdapterInterface
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var devices = ArrayList<Device>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val binding =
            ItemHomeDeviceBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return DeviceViewHolder(
            binding
        )
    }

    override fun getItemCount(): Int {
        return devices.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as DeviceViewHolder).bind(position)
    }

    private inner class DeviceViewHolder(private val binding: ItemHomeDeviceBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            val device = devices[position]

            with(binding) {

                itemHomeDeviceStatusBackground.setCardBackgroundColor(
                    if (device.isUpdating) {
                        Color.YELLOW
                    } else if (device.status) {
                        Color.GREEN
                    } else {
                        Color.RED
                    }
                )

                itemHomeDeviceImage.setImageResource(getDeviceIconByType(device.type))

                itemHomeDeviceType.setText(
                    when (device.type) {
                        TYPE_LEDCONTROLLER -> R.string.led
                        TYPE_LIGHTCONTROLLER -> R.string.light
                        TYPE_WATERCONTROLLER -> R.string.waterController
                        else -> R.string.other
                    }
                )

                itemHomeDeviceSwitch.setOnClickListener {
                    if (device.state) {
                        callback.turnOffDevice(deviceId = device.id)
                    } else {
                        callback.turnOnDevice(deviceId = device.id)
                    }
                }
                itemHomeDeviceSwitch.isChecked = device.state

                itemHomeDeviceName.text = device.name

                itemHomeDevice.setOnClickListener {
                    callback.onDeviceClick(device.id)
                }

                itemHomeDevice.setOnLongClickListener {

                    showDeleteMenu(it, deviceId = device.id, position = position)

                    return@setOnLongClickListener true
                }
            }

        }
    }

    fun updateDeviceList(deviceList: ArrayList<Device>) {
        devices = sortDeviceList(deviceList)

        notifyDataSetChanged()
    }

    private fun showDeleteMenu(view: View, deviceId: Int, position: Int) {
        val menu = PopupMenu(view.context, view)

        menu.menu.add(0, 0, Menu.NONE, "Delete")

        menu.setOnMenuItemClickListener {
            when (it.itemId) {

                0 -> {
                    callback.deleteDevice(deviceId)
                    Toast.makeText(view.context, "deviceDeleted", Toast.LENGTH_SHORT).show()
                    notifyItemRemoved(position)
                }

            }
            return@setOnMenuItemClickListener true
        }

        menu.show()
    }

    private fun sortDeviceList(list: ArrayList<Device>): ArrayList<Device> {

        val typesArray = TYPES_ARRAY

        val sortedList = ArrayList<Device>()

        repeat(typesArray.size) { typePosition ->
            repeat(list.size) { devicePosition ->

                if (list[devicePosition].type == typesArray[typePosition]) {
                    sortedList.add(list[devicePosition])
                }

            }
        }

        repeat(list.size) { typePosition ->
            if (!typesArray.contains(list[typePosition].type)) {
                sortedList.add(list[typePosition])
            }
        }

        return sortedList
    }

}