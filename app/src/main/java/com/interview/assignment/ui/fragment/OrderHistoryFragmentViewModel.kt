package com.interview.assignment.ui.fragment

import android.content.Context
import android.util.Log
import com.application.base.AppBaseViewModel
import com.interview.assignment.MyApplication.Companion.appRepository
import com.interview.assignment.model.OrderHistoryResponse
import com.interview.assignment.util.checkNetworkConnectionDialog

class OrderHistoryFragmentViewModel: AppBaseViewModel() {

    companion object {
        lateinit var mContext: Context

        @JvmStatic
        fun newInstance(context: Context) = OrderHistoryFragmentViewModel().apply {
            mContext = context
        }
    }

    fun getOrderHistory(page:Int,responseBack: (List<OrderHistoryResponse>) -> Unit) {
        // API Calling
        callApi(
            mContext, appRepository.getOrderHistory(page),
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
                Log.e("OrderHistory", it.message.toString())
            }
        )
    }
}
