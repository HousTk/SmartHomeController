package com.example.smartcontrollerv3.main.presentation.addresses

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.domain.models.main.Room
import com.example.smartcontrollerv3.databinding.ItemFragmentAddressesRoomBinding

class AddressesRoomsAdapter() : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var roomsList = emptyList<Room>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ItemFragmentAddressesRoomBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return RoomHolder(binding)
    }

    override fun getItemCount(): Int {
        return roomsList.size - 1
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        (holder as RoomHolder).bind(position)

    }

    private inner class RoomHolder(private val binding: ItemFragmentAddressesRoomBinding) :
        RecyclerView.ViewHolder(binding.root) {

            fun bind(position: Int){

                val room = roomsList[position + 1]

                with(binding){

                    itemFragmentAddressesRoomName.text = room.name
                    itemFragmentAddressesRoomIcon.setImageResource(room.icon)
                }

            }
    }


    fun updateRoomsList(list: List<Room>) {
        roomsList = list
        notifyDataSetChanged()
    }

}