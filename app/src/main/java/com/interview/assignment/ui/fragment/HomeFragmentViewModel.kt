package com.interview.assignment.ui.fragment

import android.content.Context
import com.application.base.AppBaseViewModel
import com.interview.assignment.MyApplication.Companion.appRepository
import com.interview.assignment.model.RestaurantResponse
import com.interview.assignment.util.checkNetworkConnectionDialog
import com.interview.assignment.util.hideDialog
import com.interview.assignment.util.showDialog

class HomeFragmentViewModel: AppBaseViewModel() {

    companion object {
        lateinit var mContext: Context

        @JvmStatic
        fun newInstance(context: Context) = HomeFragmentViewModel().apply {
            mContext = context
        }
    }

    fun getRestaurantData(responseBack: (RestaurantResponse) -> Unit) {
        // API Calling
        callApi(
            mContext, appRepository.getRestaurantData(),
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
