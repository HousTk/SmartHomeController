package com.example.smartcontrollerv3.main.presentation.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.smartcontrollerv3.R
import com.example.smartcontrollerv3.databinding.FragmentProfileBinding
import org.koin.androidx.viewmodel.ext.android.viewModel


class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding

    private val vm by viewModel<ProfileViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentProfileBinding.inflate(inflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initUi()
    }

    private fun initUi(){

        with(binding){

            with(fragmentProfileToolbar){

                toolbarStockBack.setOnClickListener{

                }

                toolbarStockMore.setOnClickListener{
                    vm.onClickMore(it)
                }

                toolbarStockText.text = "Profile"

            }

        }
    }

}