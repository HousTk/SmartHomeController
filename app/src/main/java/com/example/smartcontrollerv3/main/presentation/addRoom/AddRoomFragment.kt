package com.example.smartcontrollerv3.main.presentation.addRoom

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.core.view.isVisible
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.smartcontrollerv3.R
import com.example.smartcontrollerv3.databinding.FragmentAddRoomBinding
import com.example.smartcontrollerv3.main.confirmationDialogs.ConfirmationDialogThreeButtons
import com.example.smartcontrollerv3.main.confirmationDialogs.ConfirmationDialogThreeButtonsCallback
import org.koin.androidx.viewmodel.ext.android.viewModel


class AddRoomFragment : Fragment() {

    private lateinit var binding: FragmentAddRoomBinding
    private lateinit var devicesAdapter: AddRoomDeviceAdapter
    private lateinit var iconsAdapter: AddRoomIconAdapter

    private val vm by viewModel<AddRoomViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requireActivity().onBackPressedDispatcher.addCallback(this) {
            onClickBack()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddRoomBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        initAdapters()

        initUi()

        initVMobservers()

    }

    private fun initAdapters() {

        devicesAdapter = AddRoomDeviceAdapter(vm.getDeviceList())

        iconsAdapter = AddRoomIconAdapter(
            object : AddRoomIconAdapterInterface {
                override fun onClickIcon(icon: Int) {
                    vm.selectIcon(icon)
                    changeIconsRV()
                }

            }
        )

    }

    private fun initUi() {
        with(binding) {

            fragmentAddRoomAddDevicesRV.layoutManager = LinearLayoutManager(
                this.fragmentAddRoomAddDevicesRV.context,
                LinearLayoutManager.HORIZONTAL,
                false
            )
            fragmentAddRoomAddDevicesRV.adapter = devicesAdapter

            fragmentAddRoomAddDevicesRV.visibility = View.GONE

            fragmentAddRoomAddDevicesCheck.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    fragmentAddRoomAddDevicesRV.visibility = View.VISIBLE
                } else {
                    fragmentAddRoomAddDevicesRV.visibility = View.GONE
                }
            }

            fragmentAddRoomIconImage.setOnClickListener {
                changeIconsRV()
            }

            fragmentAddRoomIconRV.layoutManager = GridLayoutManager(
                fragmentAddRoomAddDevicesRV.context,
                4
            )
            fragmentAddRoomIconRV.adapter = iconsAdapter


            with(fragmentAddRoomToolbar) {

                toolbarConfirmationText.text = "Add room"

                toolbarConfirmationBack.setOnClickListener {
                    onClickBack()
                }

                toolbarConfirmationSave.setOnClickListener {
                    onClickSave()
                }

            }

        }
    }

    private fun initVMobservers() {
        vm.icon.observe(requireActivity()) { image ->
            binding.fragmentAddRoomIconImage.setImageResource(image)
        }
    }

    private fun changeIconsRV() {
        with(binding) {
            if (!fragmentAddRoomIconRV.isVisible) {
                fragmentAddRoomIconRV.visibility = View.VISIBLE
                fragmentAddRoomIconFold.setImageResource(R.drawable.ic_arrom_drop_up_white)
            } else {
                fragmentAddRoomIconRV.visibility = View.GONE
                fragmentAddRoomIconFold.setImageResource(R.drawable.ic_arrow_drop_down_white)
            }
        }
    }

    private fun onClickSave() {
        saveRoom()
    }

    private fun saveRoom() {

        try {

            val name = binding.fragmentAddRoomNamePlainText.text.toString()

            val list = if (binding.fragmentAddRoomAddDevicesCheck.isChecked) {
                devicesAdapter.selectedIdsList
            } else {
                null
            }

            vm.save(name, list)

        } catch (e: Exception) {
            Toast.makeText(this.context, "${e.message}", Toast.LENGTH_SHORT).show()
        }

    }

    private fun onClickBack() {

        val name = binding.fragmentAddRoomNamePlainText.text.toString()
        val list = if (binding.fragmentAddRoomAddDevicesCheck.isChecked) {
            devicesAdapter.selectedIdsList
        } else {
            null
        }

        if (name.isNotEmpty() || list != null) {

            val confirmationDialog = ConfirmationDialogThreeButtons(
                object : ConfirmationDialogThreeButtonsCallback {

                    override fun yesButton() {
                        saveRoom()
                    }

                    override fun noButton() {
                        vm.back()
                    }

                    override fun cancelButton() {

                    }

                },
                "Сохранить комнату?"
            )

            confirmationDialog.show(parentFragmentManager, "ConfirmationDialog")

        } else{
            vm.back()
        }
    }


}