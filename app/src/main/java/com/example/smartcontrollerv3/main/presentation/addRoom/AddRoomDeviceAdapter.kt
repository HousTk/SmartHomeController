package com.example.smartcontrollerv3.main.presentation.addRoom

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.domain.models.main.Device
import com.example.smartcontrollerv3.R
import com.example.smartcontrollerv3.databinding.ItemFragmentAddRoomDeviceBinding

class AddRoomDeviceAdapter():RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var deviceList = emptyList<Device>()

    val selectedIdsList = ArrayList<Long>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ItemFragmentAddRoomDeviceBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return DeviceViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return deviceList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as DeviceViewHolder).bind(position)
    }

    private inner class DeviceViewHolder(val binding:ItemFragmentAddRoomDeviceBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(position: Int){

            val device = deviceList[position]

            with(binding){

                itemFragmentAddRoomDeviceName.text = device.name

                itemFragmentAddRoomDevice.setOnClickListener{

                    if(selectedIdsList.contains(device.id)){
                        selectedIdsList.remove(device.id)
                        itemFragmentAddRoomDeviceImage.setImageResource(R.drawable.ic_add_white)

                    }else{
                        selectedIdsList.add(device.id)
                        itemFragmentAddRoomDeviceImage.setImageResource(R.drawable.ic_remove_white)

                    }
                }

            }

        }
    }

    fun updateDevicesList(list: List<Device>){

        deviceList = list

        notifyDataSetChanged()

    }
}
