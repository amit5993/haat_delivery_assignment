package com.interview.assignment.ui.map

import android.content.Context
import com.application.base.AppBaseViewModel
import com.interview.assignment.MyApplication.Companion.appRepository
import com.interview.assignment.model.MqttResponse
import com.interview.assignment.model.UserData
import com.interview.assignment.util.checkNetworkConnectionDialog
import com.interview.assignment.util.hideDialog
import com.interview.assignment.util.showDialog

class MapActivityViewModel: AppBaseViewModel() {

    companion object {
        lateinit var mContext: Context

        @JvmStatic
        fun newInstance(context: Context) = MapActivityViewModel().apply {
            mContext = context
        }
    }



    fun getMqttDetails(responseBack: (MqttResponse) -> Unit) {
        // API Calling
        callApi(
            mContext, appRepository.getMqttDetails(),
            onResponse = {
                responseBack(it)
            }, noInternet = {
                mContext.checkNetworkConnectionDialog()
            }, onStart = {
//                mContext.showDialog()
            }, onAuthFail = {
                // Clear AppPref and going to Login Page
            }, onFinish = {
//                mContext.hideDialog()
            }, onError = {

            }
        )
    }

}
