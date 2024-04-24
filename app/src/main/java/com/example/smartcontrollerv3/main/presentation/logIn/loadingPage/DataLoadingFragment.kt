package com.example.smartcontrollerv3.main.presentation.logIn.loadingPage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.smartcontrollerv3.R
import org.koin.androidx.viewmodel.ext.android.viewModel

class DataLoadingFragment : Fragment() {

    private val vm by viewModel<DataLoadingViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_data_loading, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        vm.init()
    }


}