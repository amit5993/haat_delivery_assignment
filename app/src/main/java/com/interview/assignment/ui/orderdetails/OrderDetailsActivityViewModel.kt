package com.interview.assignment.ui.orderdetails

import android.content.Context
import com.application.base.AppBaseViewModel
import com.interview.assignment.MyApplication.Companion.appRepository
import com.interview.assignment.model.MqttResponse
import com.interview.assignment.model.UserData
import com.interview.assignment.util.checkNetworkConnectionDialog
import com.interview.assignment.util.hideDialog
import com.interview.assignment.util.showDialog

class OrderDetailsActivityViewModel: AppBaseViewModel() {

    companion object {
        lateinit var mContext: Context

        @JvmStatic
        fun newInstance(context: Context) = OrderDetailsActivityViewModel().apply {
            mContext = context
        }
    }

}
