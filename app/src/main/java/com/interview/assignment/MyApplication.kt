package com.interview.assignment

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.multidex.MultiDex
import com.androidnetworking.AndroidNetworking
import com.chibatching.kotpref.Kotpref
import com.chibatching.kotpref.gsonpref.gson
import com.google.firebase.FirebaseApp
import com.google.gson.Gson
import com.interview.assignment.model.repositorys.AppRepository
import com.interview.assignment.util.isOnlineSync
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import java.security.SecureRandom
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

var mOnline = MutableLiveData<Boolean>().apply { setValue(false) }

class MyApplication : Application() {

    private lateinit var mContext: Context

    companion object {
        lateinit var appRepository: AppRepository
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    override fun onCreate() {
        super.onCreate()
        mContext = this

        appRepository = AppRepository()

        // Init Shared Preferences
        Kotpref.init(this)
        Kotpref.gson = Gson()

        // Init AndroidNetworking
        AndroidNetworking.initialize(applicationContext, getUnsafeOkHttpClient())

        // Init FCM
        FirebaseApp.initializeApp(this)

        // Keep Checking Internet Connection
        callKeepCheckingInternetConnection()
    }

    private fun callKeepCheckingInternetConnection() {
        // Keep checking internet connection.
        isOnlineSync(object : ConnectivityManager.NetworkCallback() {
            override fun onCapabilitiesChanged(network: Network, networkCapabilities: NetworkCapabilities) {
                val isNet = networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                val isValidated = networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
                if (mOnline.value != (isNet && isValidated)) {
                    Log.d("Internet", "Network Connected With: ${if (isValidated) "Internet" else "No Internet"} : $isNet & $isValidated")
                    CoroutineScope(Dispatchers.Main).launch { mOnline.value = (isNet && isValidated) }
                }
            }

            override fun onLost(network: Network) {
                Log.d("Internet", "Network Connected With: No Internet : $network")
                CoroutineScope(Dispatchers.Main).launch { mOnline.value = false }
                super.onLost(network)
            }
        }).also {
            Log.d("Internet", "Network Connected With: ${if (it) "Internet" else "No Internet"}")
            CoroutineScope(Dispatchers.Main).launch { mOnline.value = it }
        }
    }

}

private fun getUnsafeOkHttpClient(): OkHttpClient? {
    return try {
        // Create a trust manager that does not validate certificate chains
        val trustAllCerts: Array<TrustManager> = arrayOf(
            object : X509TrustManager {
                override fun checkClientTrusted(chain: Array<out X509Certificate>?, authType: String?) {}

                override fun checkServerTrusted(chain: Array<out X509Certificate>?, authType: String?) {}

                override fun getAcceptedIssuers(): Array<X509Certificate> {
                    return arrayOf()
                }
            }
        )

        OkHttpClient.Builder().also {
            it.sslSocketFactory(
                SSLContext.getInstance("SSL").also { ssl -> ssl.init(null, trustAllCerts, SecureRandom()) }.socketFactory,
                trustAllCerts[0] as X509TrustManager
            )
            it.hostnameVerifier { hostname, session -> true }
            it.connectTimeout(120, TimeUnit.MINUTES)
            it.readTimeout(120, TimeUnit.MINUTES)
            it.writeTimeout(120, TimeUnit.MINUTES)
        }.build()
    } catch (e: java.lang.Exception) {
        throw RuntimeException(e)
    }
}