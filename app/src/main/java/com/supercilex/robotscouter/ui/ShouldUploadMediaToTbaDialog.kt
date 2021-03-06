package com.supercilex.robotscouter.ui

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.text.method.LinkMovementMethod
import android.view.View
import android.widget.CheckBox
import android.widget.TextView
import com.supercilex.robotscouter.R
import com.supercilex.robotscouter.util.data.shouldAskToUploadMediaToTba
import com.supercilex.robotscouter.util.data.shouldUploadMediaToTba
import com.supercilex.robotscouter.util.ui.CaptureTeamMediaListener
import com.supercilex.robotscouter.util.ui.DialogFragmentBase
import com.supercilex.robotscouter.util.ui.create
import kotterknife.bindView
import org.jetbrains.anko.find

class ShouldUploadMediaToTbaDialog : DialogFragmentBase(), DialogInterface.OnClickListener {
    private val saveResponseCheckbox: CheckBox by bindView(R.id.save_response)

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog = AlertDialog.Builder(context!!)
            .setTitle(R.string.media_should_upload_title)
            .setMessage(R.string.media_should_upload_rationale)
            .setView(View.inflate(context, R.layout.dialog_should_upload_media, null))
            .setPositiveButton(R.string.yes, this)
            .setNegativeButton(R.string.no, this)
            .create {
                find<TextView>(android.R.id.message).movementMethod =
                        LinkMovementMethod.getInstance()
            }

    override fun onClick(dialog: DialogInterface, which: Int) {
        val isYes: Boolean = which == Dialog.BUTTON_POSITIVE

        if (saveResponseCheckbox.isChecked) shouldUploadMediaToTba = isYes
        (parentFragment as CaptureTeamMediaListener).startCapture(isYes)
    }

    companion object {
        private const val TAG = "ShouldUploadMediaToTbaD"

        fun <F> show(
                fragment: F
        ) where F : Fragment, F : CaptureTeamMediaListener = if (shouldAskToUploadMediaToTba) {
            ShouldUploadMediaToTbaDialog().show(fragment.childFragmentManager, TAG)
        } else {
            fragment.startCapture(shouldUploadMediaToTba)
        }
    }
}
