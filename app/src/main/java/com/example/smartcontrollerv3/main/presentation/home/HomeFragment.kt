package com.example.smartcontrollerv3.main.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.domain.domain.callbacks.OnCompleteCallback
import com.example.domain.domain.models.main.Device
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
        vm.setLifecycleOwner(requireActivity())
        vm.onCreateView()

    }

    override fun onDestroyView() {
        super.onDestroyView()

    }

    override fun onStop() {
        super.onStop()
        vm.onStopView()
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

                toolbarProfile.setOnClickListener{
                    vm.onClickProfile()
                }


            }
            fragmentHomeFloatingButton.setOnClickListener{
                vm.onClickAddDevice()
            }

            fragmentHomeRefresh.setOnRefreshListener {

                vm.refresh(
                    callback = object : OnCompleteCallback {

                        override fun onSuccess() {
                            fragmentHomeRefresh.isRefreshing = false
                        }

                        override fun onFail(message: String) {
                            TODO("Not yet implemented")
                        }


                    }
                )

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


    }

    private fun initAdapters(){

        roomsAdapter = HomeRoomNameAdapter(
            object : HomeRoomNameAdapterInterface{

                override fun onRoomClick(roomId: Long, roomPosition: Int) {
                    vm.onClickRoom(roomId = roomId, roomPosition = roomPosition)
                }

                override fun onDeleteRoom(roomId: Long) {
                    vm.deleteRoom(roomId)
                }

                override fun onCreateRoomClick() {
                    vm.onClickAddRoom()
                }

            }
        )

        devicesAdapter = HomeDeviceAdapter(
            object : HomeDeviceAdapterInterface{
                override fun onDeviceClick(device: Device) {

                    vm.onDeviceClick(deviceId = device.id)

                }

                override fun deleteDevice(deviceId: Long) {
                    vm.deleteOrRemoveDevice(deviceId)
                }

                override fun turnOnDevice(deviceId: Long) {
                    vm.sendTurnOnToDevice(deviceId)
                }

                override fun turnOffDevice(deviceId: Long) {
                    vm.sendTurnOffToDevice(deviceId)
                }

            },
            vm.selectedRoomId.value!!

        )
    }

    private fun initVMobservers(){

        vm.roomList.observe(requireActivity()){roomList ->

            roomsAdapter.updateRoomList(roomList)

        }

        vm.selectedRoomPosition.observe(requireActivity()){ selectedPosition ->

            roomsAdapter.updateSelectedRoomPosition(selectedPosition)

        }

        vm.devicesListInSelectedRoom.observe(requireActivity()){devicesList ->

            devicesAdapter.updateDeviceList(deviceList = devicesList, selectedRoomId = vm.selectedRoomId.value!!)


        }

        vm.currentAddress.observe(requireActivity()){address ->

            binding.toolBar.toolbarText.text = vm.currentAddress.value!!.name

        }
    }

}