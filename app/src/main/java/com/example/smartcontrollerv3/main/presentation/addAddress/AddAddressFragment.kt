package com.example.smartcontrollerv3.main.presentation.addAddress

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import com.example.domain.domain.models.main.Address
import com.example.domain.domain.models.saveParams.SaveParamsAddress
import com.example.smartcontrollerv3.databinding.FragmentAddAddressBinding
import com.example.smartcontrollerv3.main.confirmationDialogs.ConfirmationDialogThreeButtons
import com.example.smartcontrollerv3.main.confirmationDialogs.ConfirmationDialogThreeButtonsCallback
import org.koin.androidx.viewmodel.ext.android.viewModel


class AddAddressFragment : Fragment() {

    private lateinit var binding: FragmentAddAddressBinding

    private val vm by viewModel<AddAddressViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddAddressBinding.inflate(inflater)

        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requireActivity().onBackPressedDispatcher.addCallback(this){
            onClickBack()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        initUI()

    }

    private fun initUI(){
        with(binding){

            with(FragmentAddAddressToolbar) {

                toolbarConfirmationBack.setOnClickListener {
                    onClickBack()
                }

                toolbarConfirmationSave.setOnClickListener{
                    saveAddress()
                }

                toolbarConfirmationText.text = "New Address"
            }


        }
    }


    private fun onClickBack(){

        val address = getAddress()

        if(address.name != "" || address.wifiSSID != ""){

            val confirmationDialog = ConfirmationDialogThreeButtons(
                object : ConfirmationDialogThreeButtonsCallback{
                    override fun yesButton() {
                        saveAddress()
                    }

                    override fun noButton() {
                        vm.navigateBack()
                    }

                    override fun cancelButton() {

                    }

                },
                titleText = "Сохранить адрес?"
            )

            confirmationDialog.show(parentFragmentManager,"")

        }else{
            vm.navigateBack()
        }

    }

    private fun saveAddress(){

        val address = getAddress()

        if(address.name != ""){
            if(address.wifiSSID != ""){

                vm.saveAddress(address = address)

            }else{
                Toast.makeText(requireContext(), "EnterSSID!", Toast.LENGTH_SHORT).show()
            }

        }else{
            Toast.makeText(requireContext(), "EnterName!", Toast.LENGTH_SHORT).show()
        }

    }

    private fun getAddress(): SaveParamsAddress {

        val name = binding.FragmentAddAddressName.text.toString()

        val ssid = binding.FragmentAddAddressSSID.text.toString()

        return SaveParamsAddress(name = name, wifiSSID = ssid)

    }
}