package com.example.smartcontrollerv3.main.presentation.addresses

import android.content.Context
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.domain.models.address.Address
import com.example.smartcontrollerv3.R
import com.example.smartcontrollerv3.databinding.ItemFragmentAddressesAddressBinding

class AddressAdapter(
    private val callback: AddressAdapterInterface,
    private var addressesList: ArrayList<Address>,
    private var selectedAddressPosition: Int
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ItemFragmentAddressesAddressBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return AddressViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return addressesList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as AddressViewHolder).bind(position)
    }

    private inner class AddressViewHolder(private val binding: ItemFragmentAddressesAddressBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            with(binding) {
                val address = addressesList[position]

                itemFragmentAddressesAddressName.text = address.name

                itemFragmentAddressesAddress.setBackgroundColor(
                    if (position == selectedAddressPosition) {
                        ContextCompat.getColor(
                            itemFragmentAddressesAddress.context,
                            R.color.itemRoomSelected
                        )
                    } else {
                        ContextCompat.getColor(
                            itemFragmentAddressesAddress.context,
                            R.color.itemRoom
                        )
                    }
                )

                itemFragmentAddressesAddress.setOnClickListener {
                    selectedAddressPosition = position
                    callback.onAddressSelect(position)
                    notifyDataSetChanged()
                }

                itemFragmentAddressesAddress.setOnLongClickListener {
                    showDeleteMenu(it, position)
                    return@setOnLongClickListener true
                }
            }
        }

    }

    private fun showDeleteMenu(view: View, position: Int) {

        if(addressesList.size != 1) {

            val menu = PopupMenu(view.context, view)

            menu.menu.add(0, 0, Menu.NONE, "Delete")

            menu.setOnMenuItemClickListener {
                when (it.itemId) {

                    0 -> {
                        deleteAddress(position)
                    }

                }
                return@setOnMenuItemClickListener true
            }

            menu.show()
        }
    }

    private fun deleteAddress(position: Int) {

        callback.addressDelete(position)
        notifyItemRemoved(position)

    }

    fun updateAddressList(addressList: ArrayList<Address>) {
        addressesList = addressList
        notifyDataSetChanged()
    }

    fun updateSelectedAddress(addressPosition:Int){
        selectedAddressPosition = addressPosition
    }
}