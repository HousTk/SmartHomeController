package com.example.domain.domain.okhttp

import com.example.domain.domain.models.device.settings.Settings
import com.example.domain.domain.repository.SettingsRepository
import com.example.domain.domain.utils.TYPE_LEDCONTROLLER
import com.example.domain.domain.utils.TYPE_LIGHTCONTROLLER
import com.example.domain.domain.utils.TYPE_MOVE_LIGHTCONTROLLER
import com.example.domain.domain.utils.TYPE_SWITCH_LIGHTCONTROLLER
import com.example.domain.domain.utils.TYPE_SWITCH_MOVE_LIGHTCONTROLLER
import com.example.domain.domain.utils.TYPE_WATERCONTROLLER
import com.example.domain.domain.utils.TYPE_WATERHEATER
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException

private const val KEY_GETTYPE = "getType"
private const val KEY_GETSETTS = "getSetts"
private const val KEY_GETSTATE = "getState"
private const val KEY_OFF = "off"
private const val KEY_ON = "on"
private const val KEY_SETTINGS = "settings"

private const val RESULT_SUCCESSFUL = "Successful"
private const val RESULT_ERROR = "Error"
private const val RESULT_LED = "LedController"
private const val RESULT_WATER = "WaterController"
private const val RESULT_LIGHT = "LightController"
private const val RESULT_WATER_HEATER = "WaterHeater"
private const val RESULT_SWITCH_MOVE_LIGHTCONTROLLER = "SwitchMoveLightController"
private const val RESULT_MOVE_LIGHTCONTROLLER = "MoveLightController"
private const val RESULT_SWITCH_LIGHTCONTROLLER = "SwitchLightController"

//private const val PARSETYPE_PARSE_FOR_STATE = -1

class WifiHandler(
    private val settingsRepository: SettingsRepository
) {

    private val posts = settingsRepository.getPosts()

    companion object {

        val okHttpClient = OkHttpClient().newBuilder().build()

    }

    fun getType(ip: String): Int? {

        val result = post(ip = ip, post = posts[KEY_GETTYPE]!!)

        val type:Int? = when (result){

            RESULT_LED -> TYPE_LEDCONTROLLER
            RESULT_LIGHT -> TYPE_LIGHTCONTROLLER
            RESULT_WATER -> TYPE_WATERCONTROLLER
            RESULT_WATER_HEATER -> TYPE_WATERHEATER
            RESULT_SWITCH_MOVE_LIGHTCONTROLLER -> TYPE_SWITCH_MOVE_LIGHTCONTROLLER
            RESULT_MOVE_LIGHTCONTROLLER -> TYPE_MOVE_LIGHTCONTROLLER
            RESULT_SWITCH_LIGHTCONTROLLER -> TYPE_SWITCH_LIGHTCONTROLLER
            else -> null

        }

        return type

    }

    suspend fun sendTurnOff(ip: String): Boolean? {

        val postResult = post(ip = ip, post = posts[KEY_OFF]!!)

        var settings: Settings? = null

        withContext(Dispatchers.Main) {

            if (postResult != null) {
                settings =
                    parseSettings(result = postResult)
            }
        }

        return settings?.state
    }

    suspend fun sendTurnOn(ip: String): Boolean? {

        val postResult = post(ip = ip, post = posts[KEY_ON]!!)

        var settings: Settings? = null

        withContext(Dispatchers.Main) {

            if (postResult != null) {
                settings =
                    parseSettings(result = postResult)
            }
        }

        return settings?.state

    }

    suspend fun sendSettings(ip: String, settings: Settings): Settings? {

        val postResult = post(ip = ip, post = settings.toString())

        var setts: Settings?

        withContext(Dispatchers.Main) {

            setts = if (postResult != null) {
                parseSettings(postResult)
            } else {
                null
            }

        }

        return setts
    }

    suspend fun getSettings(ip: String): Settings? {

        var settings: Settings?

        val postResult = post(ip = ip, post = posts[KEY_GETSETTS]!!)

        withContext(Dispatchers.Main) {

            settings = if (postResult != null) {
                parseSettings(postResult)
            } else {
                null
            }

        }

        return settings
    }

//    suspend fun getState(ip:String):Boolean?{
//
//        var state: Boolean?
//
//        val postResult = post(ip = ip, post = posts[KEY_GETSTATE]!!)
//
//        withContext(Dispatchers.Main){
//
//            state = if(postResult != null){
//                parseResult(postResult, PARSETYPE_PARSE_FOR_STATE) as Boolean?
//            }else{
//                null
//            }
//        }
//
//        return state
//    }


    private fun parseSettings(result: String): Settings?{

        if (result.indexOf("Settings") != -1) {

            var name: String = ""
            var state: Boolean = false
            var brightness: Int? = null
            var red: Int? = null
            var green: Int? = null
            var blue: Int? = null
            var normalState: Boolean? = null
            var targetTemp: Int? = null

            val parsingList = result.split("/")

            repeat(parsingList.size) { position ->
                val parsingElement = parsingList[position]

                if (parsingElement != "Settings") {

                    val parsedList = parsingElement.split("_")

                    when (parsedList[0]) {
                        "name" -> {
                            name = parsedList[1]
                        }

                        "state" -> {
                            state = parsedList[1] == "1"
                        }

                        "brightness" -> {
                            brightness = parsedList[1].toInt()
                        }

                        "red" -> {
                            red = parsedList[1].toInt()
                        }

                        "green" -> {
                            green = parsedList[1].toInt()
                        }

                        "blue" -> {
                            blue = parsedList[1].toInt()
                        }

                        "normalState" -> {
                            normalState = parsedList[1] == "1"
                        }

                        "targetTemp" ->{
                            targetTemp = parsedList[1].toInt()
                        }
                    }
                }
            }

            return Settings(
                state = state,

                brightness = brightness,
                red = red,
                green = green,
                blue = blue,

                normalState = normalState,

                targetTemp = targetTemp
            )



        } else {
            return null
        }

    }
//    private fun parseResult(result: String, type: Int): Any? {
//        if (result.indexOf("Settings") != -1) {
//
//            var name: String = ""
//            var state: Boolean = false
//            var brightness: Int = 0
//            var red: Int = 0
//            var green: Int = 0
//            var blue: Int = 0
//            var normalState: Boolean = true
//
//            val parsingList = result.split("/")
//
//            repeat(parsingList.size) { position ->
//                val parsingElement = parsingList[position]
//
//                if (parsingElement != "Settings") {
//
//                    val parsedList = parsingElement.split("_")
//
//                    when (parsedList[0]) {
//                        "name" -> {
//                            name = parsedList[1]
//                        }
//
//                        "state" -> {
//                            state = parsedList[1] == "1"
//                        }
//
//                        "brightness" -> {
//                            brightness = parsedList[1].toInt()
//                        }
//
//                        "red" -> {
//                            red = parsedList[1].toInt()
//                        }
//
//                        "green" -> {
//                            green = parsedList[1].toInt()
//                        }
//
//                        "blue" -> {
//                            blue = parsedList[1].toInt()
//                        }
//
//                        "normalState" -> {
//                            normalState = parsedList[1] == "1"
//                        }
//                    }
//                }
//            }
//
//            when (type) {
//
//                TYPE_WATERCONTROLLER -> {
//                    return state
//                }
//
//                PARSETYPE_PARSE_FOR_STATE -> {
//                    return state
//                }
//
//                else -> {
//                    return null
//                }
//            }
//
//        } else {
//            return null
//        }
//    }


    private fun post(ip: String, post: String): String? {

        var result: String? = null
        var request: Request

        try {

            request = Request.Builder().url("http://${ip}/${post}*").build()

        } catch (e: Exception) {

            return null
        }

        try {

            okHttpClient.newCall(request).execute().use { response ->

                if (response.isSuccessful) {
                    result = response.body!!.string()

                } else {
                    throw IOException("Unsuccessful request to device${ip}")
                }

            }


        } catch (e: IOException) {
            return null
        }

        return result

    }


}