package com.example.smartcontrollerv3.main.presentation.devices

import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.smartcontrollerv3.databinding.ItemDeviceBinding
import com.example.domain.domain.models.device.Device
import com.example.domain.domain.utils.TYPE_LEDCONTROLLER
import com.example.domain.domain.utils.TYPE_LIGHTCONTROLLER
import com.example.domain.domain.utils.TYPE_WATERCONTROLLER


class DevicesFragmentAdapter(
    private val callback:DevicesFragmentInterface
):RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object{
        var devicesList = ArrayList<Device>()

        const val ID_DEVICE = 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when(viewType){

            ID_DEVICE ->{
                val binding = ItemDeviceBinding.inflate(LayoutInflater.from(parent.context), parent, false)

                return DeviceHolder(binding)
            }

            else ->{
                throw Exception("Something went wrong")
            }
        }
    }

    override fun getItemCount(): Int {
        return devicesList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(getItemViewType(position)){
            ID_DEVICE ->{
                (holder as DeviceHolder).bind(position)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return ID_DEVICE
    }

    private inner class DeviceHolder(val binding: ItemDeviceBinding):RecyclerView.ViewHolder(binding.root){

        fun bind(position: Int){
            val device = devicesList[position]

            with(binding){
                itemDeviceName.text = device.name
                itemDeviceIp.text = device.ip

                itemDeviceType.text = when(device.type){
                    TYPE_LEDCONTROLLER -> "LedController"

                    TYPE_LIGHTCONTROLLER -> "LightController"

                    TYPE_WATERCONTROLLER -> "WaterController"

                    else -> ""
                }

                itemDeviceStatus.text = device.status.toString()

                itemDeviceDevice.setOnLongClickListener{

                    showDeleteMenu(view = it, deviceId = device.id, position)

                    return@setOnLongClickListener true
                }

            }
        }
    }

    fun updateDevices(newDevices: ArrayList<Device>){
        devicesList = newDevices

        notifyDataSetChanged()
    }

    private fun showDeleteMenu(view: View, deviceId: Int, position: Int){
        val menu = PopupMenu(view.context, view)

        menu.menu.add(0,0, Menu.NONE, "Delete")

        menu.setOnMenuItemClickListener {
            when (it.itemId){

                0 -> {
                    callback.deleteDevice(deviceId)
                    notifyItemRemoved(position)
                }

            }
            return@setOnMenuItemClickListener true
        }

        menu.show()
    }

}