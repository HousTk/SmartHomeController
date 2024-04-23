package com.example.smartcontrollerv3.main.presentation.mainActivity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment

import com.example.smartcontrollerv3.databinding.ActivityMainBinding
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainActivity : AppCompatActivity(){

    private lateinit var binging: ActivityMainBinding

    private val vm by viewModel<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binging = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binging.root)

        initUi()
    }

    private fun initUi(){

        val navController = binging.fragmentContainerView.getFragment<NavHostFragment>().navController

        vm.setupNavController(navController)


    }


}