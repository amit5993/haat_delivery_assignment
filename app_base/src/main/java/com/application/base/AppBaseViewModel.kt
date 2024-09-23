package com.application.base

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.HttpException
import com.google.gson.Gson
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import org.json.JSONObject

abstract class AppBaseViewModel : ViewModel() {

    @SuppressLint("StaticFieldLeak")
    private var context: Context? = null

    // T => Template type
    fun <T : Any> callApi(
        mContext: Context,
        observable: Observable<T>,
        onResponse: (T) -> Unit,
        noInternet: () -> Unit,
        onStart: () -> Unit,
        onFinish: () -> Unit,
        onError: (Throwable) -> Unit,
        onAuthFail: (() -> Unit)? = null
    ) {
        context = mContext
        // Check Internet connectivity
        if (!mContext.isNetworkConnectionAvailable()) {
            noInternet.invoke()
            return
        }

        observable.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<T> {
                override fun onSubscribe(d: Disposable) {
                    onStart.invoke()
                }

                override fun onNext(response: T) {
                    Log.e("API", "Success : ${Gson().toJson(response)}")
                    onResponse(response)
                }

                override fun onError(e: Throwable) {
                    onFinish.invoke()
                    onError.invoke(e)
                    if (onAuthFail != null) {
                        onResponseFailure(context!!, e) {
                            onAuthFail.invoke()
                        }
                    }
                }

                override fun onComplete() {
                    onFinish.invoke()
                }
            })
    }

    fun onResponseFailure(mContext: Context, throwable: Throwable, onAuthFail: () -> Unit) {
        context = mContext
        val error: HttpException = throwable as HttpException
        Log.e("API", "Error : ${error.message ?: ""}")
        Log.e("API", "Error : ${error.hashCode()}")
        when (error.hashCode()) {
            // InValidateData
            422 -> {
                val errorRawData = throwable.message ?: ""
                if (errorRawData.isNotEmpty()) {
                    if (errorRawData.contains("{") && errorRawData.contains(mContext.getErrorMessage())) {
                        toastError(JSONObject(errorRawData).getString(mContext.getErrorMessage()))
                    } else {
                        toastError(errorRawData)
                    }
                }
            }
            // Unauthenticated
            401 -> {
                onAuthFail.invoke()
            }
            // ForceUpdate
            426 -> {

            }
            // ServerError
            500 -> toastError("Internal server error")
            // PageNotFound
            404 -> toastError("Page not found")
            // BadRequest, Unauthorized, RequestTimeOut, Conflict, Blocked
            400, 403, 408, 409, 423 -> {
                val errorRawData = throwable.message ?: ""
                if (errorRawData.isNotEmpty()) {
                    if (errorRawData.contains("{") && errorRawData.contains(mContext.getErrorMessage())) {
                        toastError(JSONObject(errorRawData).getString(mContext.getErrorMessage()))
                    } else {
                        toastError(errorRawData)
                    }
                }
            }
        }
    }

    private fun toastError(message: String) {
        context?.apply {
            Toast.makeText(context, message, Toast.LENGTH_LONG).show()
        }
    }

}