package com.example.smartcontrollerv3.main.presentation.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.domain.domain.models.room.Room
import com.example.smartcontrollerv3.databinding.FragmentHomeBinding
import org.koin.androidx.viewmodel.ext.android.viewModel


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var roomsAdapter:HomeRoomNameAdapter
    private lateinit var devicesAdapter:HomeDeviceAdapter

    private val vm by viewModel<HomeViewModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentHomeBinding.inflate(inflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initAdapters()
        initUi()
        initVMobservers()
        vm.onCreateView()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        vm.onDestroyView()
    }

    private fun onClickBack(){

    }

    private fun initUi(){

        with(binding){

            with(toolBar){
                toolbarMore.setOnClickListener{
                    vm.onClickMore(it)
                }

                toolbarText.setOnClickListener{
                    vm.onClickAddress(it)
                }
            }
            fragmentHomeFloatingButton.setOnClickListener{
                vm.onClickAddDevice()
            }

            fragmentHomeRVrooms.layoutManager = LinearLayoutManager(
                fragmentHomeRVrooms.context,
                LinearLayoutManager.HORIZONTAL,
                false
            )
            fragmentHomeRVrooms.adapter = roomsAdapter

            fragmentHomeRVdevices.layoutManager = GridLayoutManager(
                fragmentHomeRVdevices.context,
                2
            )
            fragmentHomeRVdevices.adapter = devicesAdapter
        }

        vm.updateRoomList()

    }

    private fun initAdapters(){

        roomsAdapter = HomeRoomNameAdapter(
            object : HomeRoomNameAdapterInterface{
                override fun onRoomClick(position: Int) {
                    vm.onClickRoom(position)
                }

                override fun onDeleteRoom(position: Int) {
                    vm.deleteRoom(position)
                }

                override fun onCreateRoomClick() {
                    vm.onClickAddRoom()
                }

            },
            vm.selectedRoomPosition.value!!
        )

        devicesAdapter = HomeDeviceAdapter(
            object : HomeDeviceAdapterInterface{
                override fun onDeviceClick(deviceId: Int) {
                    vm.onDeviceClick(deviceId)
                }

                override fun deleteDevice(deviceId: Int) {
                    vm.deleteDevice(deviceId)
                }

                override fun turnOnDevice(deviceId: Int) {
                    vm.sendTurnOnToDevice(deviceId)
                }

                override fun turnOffDevice(deviceId: Int) {
                    vm.sendTurnOffToDevice(deviceId)
                }

            }
        )
    }

    private fun initVMobservers(){

        vm.roomList.observe(requireActivity()){roomList ->

            roomsAdapter.updateRoomList(roomList)

        }

        vm.selectedRoomPosition.observe(requireActivity()){selectedPosition ->

            roomsAdapter.updateSelectedRoom(selectedPosition)

        }

        vm.devicesListInSelectedRoom.observe(requireActivity()){devicesList ->

            devicesAdapter.updateDeviceList(deviceList = devicesList)


        }

        vm.currentAddress.observe(requireActivity()){address ->

            binding.toolBar.toolbarText.text = vm.currentAddress.value!!.name

        }
    }

    private fun updateUi(roomList: ArrayList<Room>){



    }

}