package com.interview.assignment.ui.main

import android.content.Context
import com.application.base.AppBaseViewModel
import com.interview.assignment.MyApplication.Companion.appRepository
import com.interview.assignment.model.UserData
import com.interview.assignment.util.checkNetworkConnectionDialog

class MainActivityViewModel: AppBaseViewModel() {

    companion object {
        lateinit var mContext: Context

        @JvmStatic
        fun newInstance(context: Context) = MainActivityViewModel().apply {
            mContext = context
        }
    }

}
