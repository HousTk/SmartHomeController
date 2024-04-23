package com.example.smartcontrollerv3.main.presentation.addDeviceToRoom

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.smartcontrollerv3.databinding.FragmentAddDeviceToRoomBinding


class AddDeviceToRoomFragment : Fragment() {

    private lateinit var binding: FragmentAddDeviceToRoomBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentAddDeviceToRoomBinding.inflate(inflater)

        return binding.root
    }



}