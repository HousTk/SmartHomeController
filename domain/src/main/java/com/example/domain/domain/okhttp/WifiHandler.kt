package com.example.domain.domain.okhttp

import com.example.domain.domain.models.device.settings.LedControllerSetts
import com.example.domain.domain.models.device.settings.LightControllerSetts
import com.example.domain.domain.utils.TYPE_LEDCONTROLLER
import com.example.domain.domain.utils.TYPE_LIGHTCONTROLLER
import com.example.domain.domain.utils.TYPE_WATERCONTROLLER
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException

private const val POST_GETTYPE = "GetType"
private const val POST_GETSETTS = "GetSetts"
private const val POST_GETSTATE = "GetState"
private const val POST_OFF = "TurnOff"
private const val POST_ON = "TurnOn"
private const val POST_SETTINGS = "Settings"

private const val RESULT_SUCCESSFUL = "Successful"
private const val RESULT_ERROR = "Error"
private const val RESULT_LED = "LedController"
private const val RESULT_WATER = "WaterController"
private const val RESULT_LIGHT = "LightController"

private const val PARSETYPE_PARSE_FOR_STATE = -1

class WifiHandler() {

    companion object {

        val okHttpClient = OkHttpClient()

    }

    fun getType(ip: String): Int? {

        val result = post(ip = ip, post = POST_GETTYPE)

        val type:Int? = when (result){

            RESULT_LED -> TYPE_LEDCONTROLLER
            RESULT_LIGHT -> TYPE_LIGHTCONTROLLER
            RESULT_WATER -> TYPE_WATERCONTROLLER
            else -> null

        }

        return type

    }

    suspend fun sendTurnOff(ip: String): Boolean? {

        val postResult = post(ip = ip, post = POST_OFF)

        var state: Boolean? = null

        withContext(Dispatchers.Main) {

            if (postResult != null) {
                state =
                    parseResult(result = postResult, type = PARSETYPE_PARSE_FOR_STATE) as Boolean
            }
        }

        return state
    }

    suspend fun sendTurnOn(ip: String): Boolean? {

        val postResult = post(ip = ip, post = POST_ON)

        var state: Boolean? = null

        withContext(Dispatchers.Main) {

            if (postResult != null) {
                state =
                    parseResult(result = postResult, type = PARSETYPE_PARSE_FOR_STATE) as Boolean?
            }
        }

        return state

    }

    suspend fun sendSettings(ip: String, settings: Any, deviceType: Int): Any? {

        val postResult = post(ip = ip, post = settings.toString())

        var settings: Any?

        withContext(Dispatchers.Main) {

            settings = if (postResult != null) {
                parseResult(postResult, deviceType)
            } else {
                null
            }

        }

        return settings
    }

    suspend fun getSettings(deviceType: Int, ip: String): Any? {

        var settings: Any?

        val postResult = post(ip = ip, post = POST_GETSETTS)

        withContext(Dispatchers.Main) {

            settings = if (postResult != null) {
                parseResult(postResult, deviceType)
            } else {
                null
            }

        }

        return settings
    }

    suspend fun getState(ip:String):Boolean?{

        var state: Boolean?

        val postResult = post(ip = ip, post = POST_GETSTATE)

        withContext(Dispatchers.Main){

            state = if(postResult != null){
                parseResult(postResult, PARSETYPE_PARSE_FOR_STATE) as Boolean?
            }else{
                null
            }
        }

        return state
    }


    private fun parseResult(result: String, type: Int): Any? {
        if (result.indexOf("Settings") != -1) {

            var name: String = ""
            var state: Boolean = false
            var brightness: Int = 0
            var red: Int = 0
            var green: Int = 0
            var blue: Int = 0
            var normalState: Boolean = true

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
                    }
                }
            }

            when (type) {

                TYPE_LEDCONTROLLER -> {
                    return LedControllerSetts(
                        brightness = brightness,
                        red = red,
                        green = green,
                        blue = blue
                    )
                }

                TYPE_LIGHTCONTROLLER -> {
                    return LightControllerSetts(
                        normalState = normalState
                    )
                }

                TYPE_WATERCONTROLLER -> {
                    return state
                }

                PARSETYPE_PARSE_FOR_STATE -> {
                    return state
                }

                else -> {
                    return null
                }
            }

        } else {
            return null
        }
    }


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