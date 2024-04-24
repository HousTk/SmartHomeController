package com.example.smartcontrollerv3.main.presentation.home

import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.domain.models.main.Room
import com.example.domain.domain.utils.ALLDEVICES_ROOM_ID
import com.example.domain.domain.utils.ALLDEVICES_ROOM_NAME
import com.example.smartcontrollerv3.R
import com.example.smartcontrollerv3.databinding.ItemHomeRoomBinding

class HomeRoomNameAdapter(
    private val homeRoomNameAdapterInterface: HomeRoomNameAdapterInterface,
    private var selectedRoomPosition:Int = 0
):RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var roomList = ArrayList<Room>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val binding = ItemHomeRoomBinding.inflate(LayoutInflater.from(parent.context),parent,false)

        return RoomViewHolder(binding)

    }

    override fun getItemCount(): Int {
        return roomList.size + 1
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as RoomViewHolder).bind(position)
    }

    private inner class RoomViewHolder(private val binding:ItemHomeRoomBinding):RecyclerView.ViewHolder(binding.root){


        fun bind(position: Int){

            if(position < roomList.size){

                val room = roomList[position]

                binding.itemHomeRoomName.text = room.name

                binding.itemHomeRoom.setOnClickListener{
                    selectedRoomPosition = position
                    homeRoomNameAdapterInterface.onRoomClick(roomId = room.id, position)

                }

                binding.itemHomeRoom.setOnLongClickListener{

                    if(room.id != ALLDEVICES_ROOM_ID.toLong()){
                        showDeleteMenu(it, position, room.id)
                    }

                    return@setOnLongClickListener true

                }

                binding.itemHomeRoom.setBackgroundColor(
                    if(selectedRoomPosition == position){
                        ContextCompat.getColor(binding.itemHomeRoom.context, R.color.itemRoomSelected)
                    } else {
                        ContextCompat.getColor(binding.itemHomeRoom.context, R.color.itemRoom)
                    }
                )

                binding.itemHomeRoomIcon.setImageResource(room.icon)

            }else{
                binding.itemHomeRoom.setBackgroundColor(
                    ContextCompat.getColor(binding.itemHomeRoom.context, R.color.itemRoom)
                )
                binding.itemHomeRoomName.text = "New room"

                binding.itemHomeRoomIcon.setImageResource(R.drawable.ic_add_white)

                binding.itemHomeRoom.setOnClickListener {
                    homeRoomNameAdapterInterface.onCreateRoomClick()
                }
            }
        }
    }

    fun updateRoomList(newRoomList: ArrayList<Room>) {
        roomList = newRoomList

        notifyDataSetChanged()
    }

    fun updateSelectedRoomPosition(selectedRoomPositionNew:Int){
        selectedRoomPosition = selectedRoomPositionNew

        notifyDataSetChanged()
    }

    private fun showDeleteMenu(view: View,roomPosition:Int ,roomId: Long){
        val menu = PopupMenu(view.context, view)

        menu.menu.add(0,0, Menu.NONE, "Delete")

        menu.setOnMenuItemClickListener {
            when (it.itemId){

                0 -> {
                    homeRoomNameAdapterInterface.onDeleteRoom(roomId)
                    Toast.makeText(view.context, "roomDeleted", Toast.LENGTH_SHORT).show()
                    notifyItemRemoved(roomPosition)
                }

            }
            return@setOnMenuItemClickListener true
        }

        menu.show()
    }
}