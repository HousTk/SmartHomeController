package com.example.smartcontrollerv3.main.presentation.addresses

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.smartcontrollerv3.databinding.FragmentAddressesBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class AddressesFragment : Fragment() {

    private lateinit var binding:FragmentAddressesBinding
    private lateinit var addressesAdapter:AddressAdapter

    private val vm by viewModel<AddressesViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentAddressesBinding.inflate(inflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initAdapters()

        initUi()

        updateUi()

    }

    private fun initUi(){
        with(binding){

            with(fragmentAddressesToolbar){
                toolbarStockBack.setOnClickListener{
                    vm.back()
                }
                toolbarStockText.text = "Addresses"
            }

            fragmentAddressesRV.layoutManager = LinearLayoutManager(
                fragmentAddressesRV.context,
                LinearLayoutManager.VERTICAL,
                false
            )

            fragmentAddressesRV.adapter= addressesAdapter

            fragmentAddressesNewAddress.setOnClickListener{
                vm.navigateToNewAddress()
            }

        }
    }

    private fun updateUi(){
        val address = vm.getSelectedAddress()

        with(binding){
            fragmentAddressesName.text = address.name
            fragmentAddressesSSID.text = address.wifiSSID
        }
    }

    private fun initAdapters(){
        addressesAdapter = AddressAdapter(
            object : AddressAdapterInterface{

                override fun onAddressSelect(position: Int) {
                    vm.selectAddress(selectedAddressPosition = position)
                    updateUi()
                }

                override fun addressDelete(addressPosition: Int) {
                    vm.deleteAddress(addressPosition)
                    addressesAdapter.updateAddressList(vm.getAddressesList())
                    addressesAdapter.updateSelectedAddress(vm.getSelectedAddressPosition())
                    updateUi()
                }

            },
            vm.getAddressesList(),
            vm.getSelectedAddressPosition()
        )
    }

}