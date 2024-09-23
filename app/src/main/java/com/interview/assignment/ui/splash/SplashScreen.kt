package com.interview.assignment.ui.splash

import android.content.Intent
import android.os.Handler
import android.os.Looper
import com.application.base.AppBaseActivity
import com.interview.assignment.R
import com.interview.assignment.databinding.ActivitySplashScreenBinding
import com.interview.assignment.ui.main.MainActivity
import com.interview.assignment.util.checkBackPress
import com.interview.assignment.util.doubleBackToExitPressedOnce

class SplashScreen : AppBaseActivity<ActivitySplashScreenBinding, SplashScreenViewModel>() {

    private val handler = Handler(Looper.getMainLooper())

    override fun setViewBinding() = ActivitySplashScreenBinding.inflate(layoutInflater)

    override fun setViewModel() = SplashScreenViewModel.newInstance(this)

    override fun initView() {
        handler.postDelayed({
            startActivity(Intent(this, MainActivity::class.java).also {
                it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            })
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        }, 3000)
    }

    override fun initOnClick() {
        // Nothing
    }

    override fun appOnBackPressed() {
        if (doubleBackToExitPressedOnce) {
            finish()
        } else {
            checkBackPress()
        }
    }

}