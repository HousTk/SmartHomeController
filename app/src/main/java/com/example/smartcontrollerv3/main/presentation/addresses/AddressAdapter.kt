package com.example.smartcontrollerv3.main.presentation.addresses

import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.domain.models.main.Address
import com.example.smartcontrollerv3.R
import com.example.smartcontrollerv3.databinding.ItemFragmentAddressesAddressBinding

class AddressAdapter(
    private val callback: AddressAdapterInterface
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var addressesList = emptyList<Address>()
    private var selectedAddressKey:String? = null

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
                    if (address.key == selectedAddressKey) {
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
                    selectedAddressKey = address.key
                    callback.onAddressSelect(address.key)
                    notifyDataSetChanged()
                }

                itemFragmentAddressesAddress.setOnLongClickListener {
                    showDeleteMenu(view = it, addressKey = address.key, position = position)
                    return@setOnLongClickListener true
                }
            }
        }

    }

    private fun showDeleteMenu(view: View, addressKey: String, position: Int) {

        if(addressesList.size != 1) {

            val menu = PopupMenu(view.context, view)

            menu.menu.add(0, 0, Menu.NONE, "Delete")

            menu.setOnMenuItemClickListener {
                when (it.itemId) {

                    0 -> {
                        deleteAddress(addressKey,position)
                    }

                }
                return@setOnMenuItemClickListener true
            }

            menu.show()
        }
    }

    private fun deleteAddress(addressKey: String, position: Int) {

        callback.addressDelete(addressKey)

        notifyItemRemoved(position)

    }

    fun updateAddressList(addressList: List<Address>) {
        addressesList = addressList
        notifyDataSetChanged()
    }

    fun updateSelectedAddress(addressKey:String){

        val oldSelectedAddressKey = selectedAddressKey

        selectedAddressKey = addressKey

        repeat(addressesList.size){

            val address = addressesList[it]

            if(address.key == addressKey || address.key == oldSelectedAddressKey){

                notifyItemChanged(it)

            }

        }


    }
}