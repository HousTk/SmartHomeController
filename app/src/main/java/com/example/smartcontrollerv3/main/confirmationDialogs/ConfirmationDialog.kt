package com.example.smartcontrollerv3.main.confirmationDialogs

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import kotlin.concurrent.timerTask


class ConfirmationDialogThreeButtons(
    private val callback: ConfirmationDialogThreeButtonsCallback,
    private val titleText: String
) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder

                .setTitle(titleText)

                .setNeutralButton("Yes") { dialog, id ->

                    callback.yesButton()
                    dialog.cancel()

                }

                .setNegativeButton("No") { dialog, id ->

                    callback.noButton()
                    dialog.cancel()

                }

                .setPositiveButton("Cancel") { dialog, id ->

                    callback.cancelButton()
                    dialog.cancel()

                }

            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

}

interface ConfirmationDialogThreeButtonsCallback {
    fun yesButton()
    fun noButton()
    fun cancelButton()
}


class ConfirmationDialogTwoButtons(
    private val callback: ConfirmationDialogTwoButtonsCallback,
    private val titleText: String
) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder

                .setTitle(titleText)


                .setNegativeButton("No") { dialog, id ->

                    callback.noButton()
                    dialog.cancel()

                }

                .setPositiveButton("Yes") { dialog, id ->

                    callback.yesButton()
                    dialog.cancel()

                }

            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

}

interface ConfirmationDialogTwoButtonsCallback {
    fun yesButton()
    fun noButton()
}