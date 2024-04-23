package com.example.smartcontrollerv3.main.navigationController

import android.os.Bundle
import androidx.navigation.NavController
import com.example.domain.domain.navController.NavigationControllerInterface
import com.example.domain.domain.usecase.settings.IsFirstStartUseCase
import com.example.smartcontrollerv3.R
import java.lang.IllegalArgumentException
import java.lang.IllegalStateException

const val KEY_SELECTED_ROOM_POSITION = "SelRoomPos"

class NavigationController(
    private val isFirstStartUseCase: IsFirstStartUseCase
) : NavigationControllerInterface {

    data class BundleWithKey(
        var bundleKey: String,
        var bundle:Bundle
    )

    private lateinit var navHost: NavController

    private val bundleList = ArrayList<BundleWithKey>()

    fun putArgsToBundle(bundleKey:String, argKey: String, arg: Any):Boolean{

        var bundle = Bundle()

        repeat(bundleList.size){
            if(bundleList[it].bundleKey == bundleKey){
                bundle = bundleList[it].bundle
            }
        }

        when (arg) {
            is Int -> {
                bundle.putInt(argKey, arg)
            }

            is String -> {
                bundle.putString(argKey, arg)
            }

            is Boolean -> {
                bundle.putBoolean(argKey, arg)
            }

            else -> {
                throw IllegalArgumentException("args type isn't support by navigateWithArgs()")
            }
        }

        repeat(bundleList.size){
            if(bundleList[it].bundleKey == bundleKey){
                bundleList[it].bundle = bundle
                return true
            }
        }

        val bundleListElement = BundleWithKey(
            bundleKey = bundleKey,
            bundle = bundle
        )

        bundleList.add(bundleListElement)
        return true

    }

    fun navigateToWithBundle(bundleKey:String, destinationPageId: Int){

        if (this::navHost.isInitialized) {

            lateinit var bundle:Bundle
            var position:Int = -1


            repeat(bundleList.size){
                if(bundleList[it].bundleKey == bundleKey){
                    bundle = bundleList[it].bundle
                    position = it
                }
            }

            navHost.navigate(destinationPageId, bundle)

            bundleList.removeAt(position)

        }

    }

    override fun navigateTo(destinationPageId: Int) {
        if (this::navHost.isInitialized) {

            navHost.navigate(destinationPageId)


        }
    }

    override fun navigateBack() {

        if (this::navHost.isInitialized) {

            navHost.popBackStack()

        }

    }


    override fun navigateWithArgs(key: String, args: Any, destinationPageId: Int) {
        if (this::navHost.isInitialized) {
            val bundle = Bundle()

            when (args) {
                is Int -> {
                    bundle.putInt(key, args)
                }

                is String -> {
                    bundle.putString(key, args)
                }

                is Boolean -> {
                    bundle.putBoolean(key, args)
                }

                else -> {
                    throw IllegalArgumentException("args type isn't support by navigateWithArgs()")
                }
            }

            navHost.navigate(destinationPageId, bundle)

        }
    }

    override fun navigateBackTo(destinationPageId: Int) {

        navHost.popBackStack(destinationPageId,false)

    }

    fun setUpNavHost(host: NavController) {

        navHost = host

        val navGraph = navHost.navInflater.inflate(R.navigation.navigation_main)

        if (!isFirstStartUseCase.execute()) {
            navGraph.setStartDestination(R.id.homeFragment)
        }else{
            navGraph.setStartDestination(R.id.createFirstAddressFragment)
        }

        navHost.graph = navGraph



    }

}