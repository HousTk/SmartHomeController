package com.example.smartcontrollerv3.main.presentation.addresses

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.domain.domain.models.main.Address
import com.example.smartcontrollerv3.databinding.FragmentAddressesBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class AddressesFragment : Fragment() {

    private lateinit var binding:FragmentAddressesBinding
    private lateinit var addressesAdapter:AddressAdapter
    private lateinit var addressesRoomsAdapter: AddressesRoomsAdapter
    private lateinit var devicesAdapter: AddressesDevicesAdapter

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

        initVMobservers()

        initVM()

    }

    private fun initAdapters(){

        addressesAdapter = AddressAdapter(
            object : AddressAdapterInterface{

                override fun onAddressSelect(addressKey: String) {
                    vm.selectAddress(addressKey)

                }

                override fun addressDelete(addressKey: String) {
                    vm.deleteAddress(addressKey)

                }

            },

        )

        addressesRoomsAdapter = AddressesRoomsAdapter(

        )

        devicesAdapter = AddressesDevicesAdapter(

        )

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


            fragmentAddressesRoomsRV.layoutManager = GridLayoutManager(
                fragmentAddressesRoomsRV.context,
                3
            )

            fragmentAddressesRoomsRV.adapter = addressesRoomsAdapter

            fragmentAddressesDevicesRV.layoutManager = GridLayoutManager(
                fragmentAddressesRoomsRV.context,
                3
            )

            fragmentAddressesDevicesRV.adapter = devicesAdapter

            fragmentAddressesNewAddress.setOnClickListener{
                vm.navigateToNewAddress()
            }

        }
    }

    private fun initVMobservers(){

        vm.selectedAddress.observe(requireActivity()){ address ->

            addressesAdapter.updateSelectedAddress(address.key)
            updateSelectedAddress(address)

        }

        vm.addressesList.observe(requireActivity()){ list ->

            addressesAdapter.updateAddressList(list)

        }

        vm.roomsList.observe(requireActivity()){ list ->

            addressesRoomsAdapter.updateRoomsList(list)

        }

        vm.devicesList.observe(requireActivity()){list ->

            devicesAdapter.updateDevicesList(list)

        }

    }

    private fun initVM(){

        vm.init()
        vm.getSelectedAddress()

    }

    private fun updateSelectedAddress(address: Address){

        with(binding){
            fragmentAddressesName.text = address.name
            fragmentAddressesSSID.text = address.wifiSSID
        }

    }



}