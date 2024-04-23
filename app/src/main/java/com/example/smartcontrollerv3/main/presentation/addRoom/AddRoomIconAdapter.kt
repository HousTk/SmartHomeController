package com.example.smartcontrollerv3.main.presentation.addRoom

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.smartcontrollerv3.databinding.ItemFragmentAddRoomIconBinding
import com.example.smartcontrollerv3.main.utils.ROOM_ICONS_ARRAY

class AddRoomIconAdapter(private val callback: AddRoomIconAdapterInterface) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val binding = ItemFragmentAddRoomIconBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return IconViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return ROOM_ICONS_ARRAY.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as IconViewHolder).bind(position)
    }

    private inner class IconViewHolder(private val binding: ItemFragmentAddRoomIconBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(position: Int) {

            binding.itemFragmentAddRoomIcon.setImageResource(ROOM_ICONS_ARRAY[position])

            binding.itemFragmentAddRoomIcon.setOnClickListener{
                callback.onClickIcon(ROOM_ICONS_ARRAY[position])
            }
        }
    }

}