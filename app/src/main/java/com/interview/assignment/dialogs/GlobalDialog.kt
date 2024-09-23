package com.interview.assignment.dialogs

import android.content.Context
import androidx.appcompat.app.AlertDialog

class GlobalDialog(
    context: Context,
    title: String, message: String? = null,
    positive: String? = null, negative: String? = null, neutral: String? = null,
    onPositiveClick: (() -> Unit)? = null, onNegativeClick: (() -> Unit)? = null, onNeutralClick: (() -> Unit)? = null,
    onDismissCall: (() -> Unit)? = null
) {

    private val builder = AlertDialog.Builder(context)
    private var dialog: AlertDialog

    init {
        builder.setTitle(title)
        if (message != null) builder.setMessage(message)
        if (positive != null) {
            builder.setPositiveButton(positive) { _, _ ->
                onDismiss()
                onPositiveClick?.invoke()
            }
        }
        if (negative != null) {
            builder.setNegativeButton(negative) { _, _ ->
                onDismiss()
                onNegativeClick?.invoke()
            }
        }
        if (neutral != null) {
            builder.setNeutralButton(neutral) { _, _ ->
                onDismiss()
                onNeutralClick?.invoke()
            }
        }
        if (onDismissCall != null) {
            builder.setOnDismissListener {
                onDismissCall.invoke()
            }
        }

        dialog = builder.create()
        dialog.setCancelable(false)
        dialog.setCanceledOnTouchOutside(false)
    }

    private fun onDismiss() {
        dialog.dismiss()
    }

    fun isShowing(): Boolean {
        return dialog.isShowing
    }

    fun onShow() {
        dialog.show()
    }

}