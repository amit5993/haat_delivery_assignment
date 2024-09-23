package com.interview.assignment.ui.splash

import android.content.Context
import com.application.base.AppBaseViewModel

class SplashScreenViewModel: AppBaseViewModel() {

    companion object {
        lateinit var mContext: Context

        @JvmStatic
        fun newInstance(context: Context) = SplashScreenViewModel().apply {
            mContext = context
        }

    }
}
