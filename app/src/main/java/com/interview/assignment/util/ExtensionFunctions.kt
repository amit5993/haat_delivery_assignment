package com.interview.assignment.util

import android.Manifest.permission
import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.location.LocationManager
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import android.os.Build
import android.os.Build.VERSION.SDK_INT
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Parcelable
import android.os.SystemClock
import android.provider.Settings
import android.telephony.SmsManager
import android.text.InputType
import android.text.TextUtils
import android.text.format.DateFormat
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.util.Patterns
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.core.app.ActivityCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.common.api.ResultCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.LocationSettingsResult
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.interview.assignment.BuildConfig
import com.interview.assignment.R
import com.interview.assignment.databinding.NoInternetBinding
import com.interview.assignment.dialogs.GlobalDialog
import com.interview.assignment.mOnline
import com.interview.assignment.pref.AppPref.NEXT_DATE_START_TIME
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.text.DecimalFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.regex.Pattern
import java.util.Currency

const val PERMISSION_REQUEST_CODE = 200
const val BLUETOOTH_REQUEST_ENABLE = 100
const val INTENT_RESULT = 100
const val INTENT_DATA = "INTENT_DATA"

const val MESSAGE_STATE_CHANGE = 1
const val MESSAGE_READ = 2
const val MESSAGE_WRITE = 3

var mLastClickTime: Long = 0
var doubleBackToExitPressedOnce = false
var options = arrayOf<CharSequence>("Take Photo", "Choose from Gallery", "Cancel")
var optionsList = arrayListOf("Take Photo", "Choose from Gallery")

internal var SpinKitProgressDialog: Dialog? = null
internal var globalDialog: GlobalDialog? = null
internal var datePickerDialog: DatePickerDialog? = null

val gson = Gson()

fun View.visibility(isVisible: Boolean) {
    visibility = if (isVisible) View.VISIBLE else View.GONE
}

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun View.isVisible(): Boolean {
    return (visibility == View.VISIBLE)
}

fun apiLog(log: String) {
    if (BuildConfig.DEBUG) Log.e("API", log)
}

fun apiLog(logs: ArrayList<String>) {
    if (BuildConfig.DEBUG) logs.forEach { Log.e("API", it) }
}

fun Context.toast(msg: String) {
    Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
}

inline fun <reified T> getData(data: Any): T {
    val jsonString = gson.toJson(data)
    return gson.fromJson<T>(jsonString, T::class.java)
}

inline fun <reified T> getDataList(data: Any): List<T> {
    val jsonString = gson.toJson(data)
    val sType = object : TypeToken<List<T>>() {}.type
    return gson.fromJson<List<T>>(jsonString, sType)
}

inline fun <reified T : Parcelable> Intent.parcelable(key: String): T? = when {
    SDK_INT >= 33 -> getParcelableExtra(key, T::class.java)
    else -> @Suppress("DEPRECATION") getParcelableExtra(key) as? T
}

inline fun <reified T : Parcelable> Bundle.parcelable(key: String): T? = when {
    SDK_INT >= 33 -> getParcelable(key, T::class.java)
    else -> @Suppress("DEPRECATION") getParcelable(key) as? T
}

inline fun <reified T : Parcelable> Bundle.parcelableArrayList(key: String): ArrayList<T>? = when {
    SDK_INT >= 33 -> getParcelableArrayList(key, T::class.java)
    else -> @Suppress("DEPRECATION") getParcelableArrayList(key)
}

inline fun <reified T : Parcelable> Intent.parcelableArrayList(key: String): ArrayList<T>? = when {
    SDK_INT >= 33 -> getParcelableArrayListExtra(key, T::class.java)
    else -> @Suppress("DEPRECATION") getParcelableArrayListExtra(key)
}

fun getPrintTextAfterSpace(item: String, max: Int): String {
    val sp = " "
    return if (item.length == max) item
    else if (item.length > max) item.substring(0, max)
    else {
        val space = java.lang.StringBuilder()
        for (i in 0 until max - item.length) {
            space.append(sp)
        }
        item.plus(space.toString())
    }
}

fun getPrintTextBeforeSpace(item: String, max: Int): String {
    val sp = " "
    return if (item.length == max) item
    else if (item.length > max) item.substring(0, max)
    else {
        val space = StringBuilder()
        for (i in 0 until (max - item.length)) {
            space.append(sp)
        }
        space.toString().plus(item)
    }
}

fun stringToDecimals(data: String): Double {
    return if (TextUtils.isEmpty(data)) 0.0
    else if (!Pattern.matches("^[-+]?\\d*[.]?\\d+|^[-+]?\\d+[.]?\\d*", data)) 0.0
    else data.toDouble()
}

fun roundTwoDecimals(data: Double, format: String? = "%.2f"): Double {
    return String.format(format ?: "%.2f", data).toDouble()
}

fun roundTwoDecimals(data: Any, format: String? = "#.##"): String {
    return DecimalFormat(format).format(data)
}

fun profileTime(): Boolean {
    val format = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.ENGLISH)
    return try {
        val date1 =
            format.parse(getTodayDate("dd/MM/yyyy", "").plus(" ").plus(NEXT_DATE_START_TIME))
                ?: Date()
        val date2 = format.parse(getTodayDate("dd/MM/yyyy HH:mm:ss", "")) ?: Date()
        date1.time < date2.time
    } catch (e: ParseException) {
        e.printStackTrace()
        false
    }
}

fun getTodayDate(format: String, replace: String? = ""): String {
    return SimpleDateFormat(format, Locale.ENGLISH).format(Calendar.getInstance().time)
        .replace(replace ?: "", "")
}

fun calculateDateDifference(startDate: String, endDate: String): Long {
    var elapsedDays: Long = 1
    val simpleDateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)
    try {
        val date1 = simpleDateFormat.parse(startDate) as Date
        val date2 = simpleDateFormat.parse(endDate) as Date
        val different = date2.time - date1.time
        val secondsInMilli: Long = 1000
        val minutesInMilli = secondsInMilli * 60
        val hoursInMilli = minutesInMilli * 60
        val daysInMilli = hoursInMilli * 24
        elapsedDays = different / daysInMilli
    } catch (e: ParseException) {
        e.printStackTrace()
    }
    return elapsedDays
}

fun Activity.getNoNetworkView(divNoData: FrameLayout, click: View.OnClickListener) {
    if (divNoData.childCount == 0) {
        divNoData.removeAllViews()
        val view = NoInternetBinding.inflate(layoutInflater, null, false)
        divNoData.addView(view.root)
        view.btnRetry.setOnClickListener(click)
    }
}

fun Context.checkBackPress() {
    doubleBackToExitPressedOnce = true
    toast(getString(R.string.please_click_back_again_to_exit))
    Handler(Looper.getMainLooper()).postDelayed({ doubleBackToExitPressedOnce = false }, 2000)
}

fun Context.dateFormat(
    currentData: String,
    currentFormat: String? = "dd/MM/yyyy",
    changeFormat: String? = "yyyy-MM-dd",
    replace: String? = ""
): String {
    return try {
        SimpleDateFormat(changeFormat, Locale.ENGLISH)
            .format(SimpleDateFormat(currentFormat, Locale.ENGLISH).parse(currentData) ?: Date())
            .replace(replace ?: "", "")
    } catch (e: ParseException) {
        e.printStackTrace()
        currentData
    }
}

/**
 * change date format as per requirement format
 * */
fun dateTimeFormatterChange(inputDate: String, format : String): String {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSSS", Locale.getDefault())
    val outputFormat = SimpleDateFormat(format, Locale.getDefault())

    try {
        val date: Date? = inputFormat.parse(inputDate)
        return if (date != null) {
            outputFormat.format(date)
        } else {
            inputDate
        }
    } catch (e: Exception) {
        return inputDate
    }
}

fun getLongToDate(timestamp: Long, dateFormat: String, multiply: Long): String {
    val calendar = Calendar.getInstance(Locale.ENGLISH)
    calendar.timeInMillis = timestamp * multiply
    return DateFormat.format(dateFormat, calendar).toString()
}

fun getStringToDate(dateFormat: String, date: String): Date {
    return SimpleDateFormat(dateFormat, Locale.ENGLISH).parse(date) ?: Date()
}

fun Context.sendImeiSMS(phoneNo: String, msg: String) {
    try {
        val smsManager = SmsManager.getDefault()
        smsManager.sendTextMessage(phoneNo, null, msg, null, null)
        this.toast("Message Sent : $msg")
    } catch (ex: Exception) {
        Toast.makeText(this, ex.message.toString(), Toast.LENGTH_LONG).show()
        ex.printStackTrace()
    }
}

fun Context.datePickerDialog(
    selectedDate: String,
    days: Int,
    onSuccess: (selectedDate: String) -> Unit
) {
    if (datePickerDialog == null) {
        val newCalendar = Calendar.getInstance()
        datePickerDialog = DatePickerDialog(
            this,
            { view, mYear, mMonthOfYear, mDayOfMonth ->
                newCalendar.set(Calendar.YEAR, mYear)
                newCalendar.set(Calendar.MONTH, mMonthOfYear)
                newCalendar.set(Calendar.DAY_OF_MONTH, mDayOfMonth)
                onSuccess.invoke(
                    SimpleDateFormat(
                        "dd/MM/yyyy",
                        Locale.ENGLISH
                    ).format(newCalendar.time)
                )
            },
            newCalendar.get(Calendar.YEAR),
            newCalendar.get(Calendar.MONTH),
            newCalendar.get(Calendar.DAY_OF_MONTH)
        ).apply {
            when (days) {
                0 -> {}
                1 -> {
                    datePicker.minDate = newCalendar.timeInMillis
                    datePicker.maxDate = newCalendar.timeInMillis
                }

                else -> {
                    val c: Calendar = incrementCalendarDay(selectedDate, "dd/MM/yyyy", (days - 1))
                    datePicker.minDate = c.timeInMillis
                    datePicker.maxDate = newCalendar.timeInMillis
                }
            }
            setOnDismissListener { datePickerDialog = null }
        }.also { datePickerDialog = it }
        if (!datePickerDialog!!.isShowing) datePickerDialog?.show()
    }
}

fun Context.datePickerDialog(
    isFromDate: String? = null,
    onSuccess: (selectedDate: String) -> Unit
) {
    if (datePickerDialog == null) {
        val newCalendar = Calendar.getInstance()
        datePickerDialog = DatePickerDialog(
            this,
            { view, mYear, mMonthOfYear, mDayOfMonth ->
                newCalendar.set(Calendar.YEAR, mYear)
                newCalendar.set(Calendar.MONTH, mMonthOfYear)
                newCalendar.set(Calendar.DAY_OF_MONTH, mDayOfMonth)
                onSuccess.invoke(
                    SimpleDateFormat(
                        "dd/MM/yyyy",
                        Locale.ENGLISH
                    ).format(newCalendar.time)
                )
            },
            newCalendar.get(Calendar.YEAR),
            newCalendar.get(Calendar.MONTH),
            newCalendar.get(Calendar.DAY_OF_MONTH)
        ).apply {
            if (isFromDate != null) {
                val calendar = Calendar.getInstance()
                try {
                    calendar.time = incrementCalendarDay(isFromDate, "dd/MM/yyyy", 1).time
                } catch (e: ParseException) {
                    e.printStackTrace()
                    calendar.add(Calendar.DATE, -1)
                }
                datePicker.minDate = calendar.timeInMillis
            }
            setOnDismissListener { datePickerDialog = null }
        }.also { datePickerDialog = it }
        if (!datePickerDialog!!.isShowing) datePickerDialog?.show()
    }
}

fun incrementCalendarDay(mDate: String, mFormat: String?, noOfDayIncrement: Int): Calendar {
    val df = SimpleDateFormat(mFormat, Locale.ENGLISH)
    val calendar = Calendar.getInstance()
    try {
        val date = df.parse(mDate) ?: Date()
        calendar.time = date
    } catch (e: ParseException) {
        e.printStackTrace()
    }
    calendar.add(Calendar.DATE, noOfDayIncrement)
    return calendar
}

fun getMyAppDate(selectedDate: String): String {
    return if (profileTime()) selectedDate else dayIncrement(
        getTodayDate("dd/MM/yyyy", ""),
        "dd/MM/yyyy",
        -1
    )
}

fun dayIncrement(mDate: String?, mFormat: String?, noOfDayIncrement: Int): String {
    val df = SimpleDateFormat(mFormat, Locale.ENGLISH)
    val calendar = Calendar.getInstance()
    try {
        val date = df.parse(mDate) ?: Date()
        calendar.time = date
    } catch (e: ParseException) {
        e.printStackTrace()
    }
    calendar.add(Calendar.DATE, noOfDayIncrement)
    return df.format(calendar.time)
}

fun Context.avoidTwoClick(): Boolean {
    return if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
        true
    } else {
        mLastClickTime = SystemClock.elapsedRealtime()
        false
    }
}

fun isValidPhoneNumber(phoneNumber: CharSequence): Boolean {
    return if (!TextUtils.isEmpty(phoneNumber) && phoneNumber.length == 10) {
        Patterns.PHONE.matcher(phoneNumber).matches()
    } else false
}

fun isValidEmail(target: CharSequence?): Boolean {
    return if (target == null) {
        false
    } else {
        Patterns.EMAIL_ADDRESS.matcher(target).matches()
    }
}

@SuppressLint("NewApi")
fun Context.isOnlineSync(networkCallback: ConnectivityManager.NetworkCallback): Boolean {
    try {
        Log.d("Internet", "isOnline() : Is called")
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork
        connectivityManager.registerDefaultNetworkCallback(networkCallback)
        return if (network != null) {
            val networkCapabilities = connectivityManager.getNetworkCapabilities(network)
            if (networkCapabilities!!.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
            ) {
                Log.d("Internet", "isOnline() : Is connected.")
                true
            } else {
                Log.d("Internet", "isOnline() : Is not connected.")
                false
            }
        } else {
            Log.d("Internet", "isOnline() : Is not connected. activeNetwork = null")
            false
        }
    } catch (ex: Exception) {
        Log.d("Internet", "isOnline() : Is not connected. Exception - ${ex.message}")
        return false
    }
}

@Suppress("DEPRECATION")
fun Context.isNetworkConnectionAvailable(): Boolean {
    val cm = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val activeNetwork = cm.activeNetworkInfo
    return (activeNetwork != null && activeNetwork.isConnected)
}

fun Context.checkInternetConnection(): Boolean {
    return if (mOnline.value == true) {
        Log.d("Network", "Connected")
        true
    } else {
        Log.d("Network", "Not Connected")
        checkNetworkConnectionDialog()
        false
    }
}

fun Context.checkNetworkConnectionDialog() {
    if (globalDialog == null) {
        globalDialog = GlobalDialog(
            this,
            title = resources.getString(R.string.no_connection),
            message = resources.getString(R.string.turn_on_connection),
            positive = getString(R.string.dialog_ok),
            onDismissCall = { globalDialog = null }
        )
        if (!globalDialog!!.isShowing()) globalDialog!!.onShow()
    }
}

fun Context.showDialog() {
    // implementation 'com.github.ybq:Android-SpinKit:1.2.0'
    if (SpinKitProgressDialog != null) {
        if (SpinKitProgressDialog!!.isShowing) return
        else SpinKitProgressDialog = null
    }

    SpinKitProgressDialog = Dialog(this).also {
        it.requestWindowFeature(Window.FEATURE_NO_TITLE)
        it.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
        it.setContentView(R.layout.progress)
        it.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        it.window?.setDimAmount(0.3f)
        it.setCancelable(false)
    }
    val lp = WindowManager.LayoutParams()
    lp.copyFrom(SpinKitProgressDialog?.window?.attributes)
    lp.width = WindowManager.LayoutParams.WRAP_CONTENT
    lp.height = WindowManager.LayoutParams.WRAP_CONTENT
    SpinKitProgressDialog?.show()
    SpinKitProgressDialog?.window?.attributes = lp
}

fun Context.hideDialog() {
    if (SpinKitProgressDialog != null) {
        if (SpinKitProgressDialog!!.isShowing) {
            SpinKitProgressDialog?.dismiss()
            SpinKitProgressDialog = null
        }
    }
}

fun ImageView.loadImage(imgUrl: Any) {
    Glide.with(this)
        .load(imgUrl)
        .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.ALL))
        .into(this)
}

fun ImageView.loadImageCenterCrop(imgUrl: Any) {
    Glide.with(this)
        .load(imgUrl)
        .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.ALL))
        .centerCrop()
        .into(this)
}

fun ImageView.loadImage(imgUrl: Any, placeholder: Drawable, error: Drawable) {
    Glide.with(this)
        .load(imgUrl)
        .placeholder(placeholder)
        .error(error)
        .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.ALL))
        .centerCrop()
        .into(this)
}

fun ImageView.loadImage(imgUrl: Any, placeholder: Int, error: Int) {
    Glide.with(this)
        .load(imgUrl)
        .placeholder(placeholder)
        .error(error)
        .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.ALL))
        .centerCrop()
        .into(this)
}

fun ImageView.loadCornerImage(data: Any, roundedCorners: Int, placeholder: Int, error: Int) {
//    val roundedCorners = context.resources.getDimensionPixelSize(R.dimen._10sdp)
    Glide.with(this)
        .load(data)
        .placeholder(placeholder)
        .error(error)
        .centerCrop()
        .transform(RoundedCorners(roundedCorners))
        .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.ALL))
        .into(this)
}

fun ImageView.loadCircleImage(imgUrl: Any, placeholder: Int, error: Int) {
    Glide.with(this)
        .load(imgUrl)
        .placeholder(placeholder)
        .error(error)
        .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.ALL))
        .centerCrop()
        .circleCrop()
        .into(this)
}

fun Context.rememberDeleteDialog(title: String, dialog_click: () -> Unit) {
    if (globalDialog == null) {
        globalDialog = GlobalDialog(
            this,
            title = title,
            message = resources.getString(R.string.are_you_sure_you_want_to_delete),
            neutral = getString(R.string.dialog_yes),
            negative = getString(R.string.dialog_no),
            onNeutralClick = { dialog_click.invoke() },
            onDismissCall = { globalDialog = null }
        )
        if (!globalDialog!!.isShowing()) globalDialog!!.onShow()
    }
}

fun Context.logoutDialog(dialog_click: () -> Unit) {
    if (globalDialog == null) {
        globalDialog = GlobalDialog(
            this,
            title = resources.getString(R.string.app_name),
            message = resources.getString(R.string.are_you_sure_you_want_to_logout),
            neutral = getString(R.string.dialog_yes),
            negative = getString(R.string.dialog_no),
            onNeutralClick = { dialog_click.invoke() },
            onDismissCall = { globalDialog = null }
        )
        if (!globalDialog!!.isShowing()) globalDialog!!.onShow()
    }
}

fun Context.loginDialog() {
    if (globalDialog == null) {
        globalDialog = GlobalDialog(
            this,
            title = resources.getString(R.string.please_sign_in),
            message = resources.getString(R.string.please_first_do_sign_in),
            positive = getString(R.string.dialog_ok),
            onDismissCall = { globalDialog = null }
        )
        if (!globalDialog!!.isShowing()) globalDialog!!.onShow()
    }
}

fun Activity.pickerImageFromGallery(startForImageResult: ActivityResultLauncher<Intent>) {
    ImagePicker.with(this)
        .galleryOnly()
        .createIntent { intent -> startForImageResult.launch(intent) }
}

fun Activity.pickerImage(startForImageResult: ActivityResultLauncher<Intent>) {
    ImagePicker.with(this)
        .cameraOnly()
        .crop()
        .createIntent { intent -> startForImageResult.launch(intent) }
}

fun Context.isAllPermissionsAllowed(): Boolean {
    val permission0 = ActivityCompat.checkSelfPermission(this, permission.READ_PHONE_STATE)
    val permission1 = ActivityCompat.checkSelfPermission(this, permission.SEND_SMS)
    val permission2 = ActivityCompat.checkSelfPermission(this, permission.WRITE_EXTERNAL_STORAGE)
    val permission3 = ActivityCompat.checkSelfPermission(this, permission.CALL_PHONE)
    return if (SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        permission0 == PackageManager.PERMISSION_GRANTED && permission1 == PackageManager.PERMISSION_GRANTED && permission3 == PackageManager.PERMISSION_GRANTED
    } else {
        permission0 == PackageManager.PERMISSION_GRANTED && permission1 == PackageManager.PERMISSION_GRANTED && permission2 == PackageManager.PERMISSION_GRANTED && permission3 == PackageManager.PERMISSION_GRANTED
    }
}

fun Activity.requestAllPermissions() {
    return if (SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(permission.READ_PHONE_STATE, permission.SEND_SMS, permission.CALL_PHONE),
            PERMISSION_REQUEST_CODE
        )
    } else {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                permission.READ_PHONE_STATE,
                permission.SEND_SMS,
                permission.WRITE_EXTERNAL_STORAGE,
                permission.CALL_PHONE
            ),
            PERMISSION_REQUEST_CODE
        )
    }
}

fun Context.isPermissionsCameraAllowed(): Boolean {
    val permission0 = ActivityCompat.checkSelfPermission(this, permission.CAMERA)
    return permission0 == PackageManager.PERMISSION_GRANTED
}

fun Activity.requestCameraPermission() {
    ActivityCompat.requestPermissions(this, arrayOf(permission.CAMERA), PERMISSION_REQUEST_CODE)
}

fun Context.isLocationPermissionsAllowed(): Boolean {
    return (ActivityCompat.checkSelfPermission(
        this,
        permission.ACCESS_FINE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED)
}

fun Activity.requestLocationPermission() {
    ActivityCompat.requestPermissions(
        this,
        arrayOf(permission.ACCESS_FINE_LOCATION),
        PERMISSION_REQUEST_CODE
    )
}

fun Context.checkGpsEnable(): Boolean {
    return (getSystemService(Context.LOCATION_SERVICE) as LocationManager).isProviderEnabled(
        LocationManager.GPS_PROVIDER
    )
}

fun Activity.showGpsDialog(callback: ResultCallback<LocationSettingsResult>?) {
    val googleApiClient = GoogleApiClient.Builder(this).addApi(LocationServices.API).build()
    googleApiClient.connect()
    val request = LocationRequest.create()
    request.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
    request.interval = 10000
    request.fastestInterval = (10000 / 2).toLong()
    val builder = LocationSettingsRequest.Builder().addLocationRequest(request)
    builder.setAlwaysShow(true)
    val result =
        LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build())
    result.setResultCallback(callback!!)
    /*
        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(LocationSettingsResult result) {
                final Status status = result.getStatus();
                final LocationSettingsStates state = result.getLocationSettingsStates();
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        // All location settings are satisfied. The client can initialize location
                        // requests here.
                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        // Location settings are not satisfied. But could be fixed by showing the user
                        // a dialog.
                        try {
                            // Show the dialog by calling startResolutionForResult(),
                            // and check the result in onActivityResult().
                            status.startResolutionForResult(activity, PERMISSION_REQUEST_CODE);
                        } catch (IntentSender.SendIntentException e) {
                            // Ignore the error.
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        // Location settings are not satisfied. However, we have no way to fix the
                        // settings so we won't show the dialog.
                        break;
                }
            }
        });*/
}

fun Activity.requestDontAskMeAgain() {
    val builder = AlertDialog.Builder(this)
    builder.setTitle(getString(R.string.app_name) + " " + getString(R.string.need_permissions))
    builder.setMessage(R.string.app_need_to_permissions)
    builder.setPositiveButton(R.string.app_setting) { dialog, which ->
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        val uri = Uri.fromParts("package", packageName, null)
        intent.data = uri
        this.startActivityForResult(intent, PERMISSION_REQUEST_CODE)
        toast(getString(R.string.go_to_permissions))
        dialog.dismiss()
    }
    builder.setCancelable(false)
    builder.show()
}

fun Context.goToLink(link: String) {
    val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
    startActivity(browserIntent)
}

fun Context.makeCall(number: String) {
    if (!TextUtils.isEmpty(number)) {
        val callIntent = Intent(Intent.ACTION_CALL)
        callIntent.data = Uri.parse("tel:${number.replace("+91 ", "")}")
        startActivity(callIntent)
    }
}

fun EditText.passwordFieldHideShow(IsShowHide: Boolean) {
    if (IsShowHide) {
        this.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
        this.transformationMethod = HideReturnsTransformationMethod.getInstance()
        this.setSelection(this.text.length)
    } else {
        this.inputType = InputType.TYPE_TEXT_VARIATION_PASSWORD
        this.transformationMethod = PasswordTransformationMethod.getInstance()
        this.setSelection(this.text.length)
    }
}

fun decodeFile(file: File, mCapturedImageURI: Uri): File? {
    val destFile = File(mCapturedImageURI.path)
    return try {
        //Decode image size
        val mOptions1 = BitmapFactory.Options()
        mOptions1.inJustDecodeBounds = true
        var fis = FileInputStream(file)
        BitmapFactory.decodeStream(fis, null, mOptions1)
        fis.close()
        val imageMaxSize = 1024
        var scale = 1
        if (mOptions1.outHeight > imageMaxSize || mOptions1.outWidth > imageMaxSize) {
            scale = Math.pow(
                2.0,
                Math.ceil(
                    Math.log(
                        imageMaxSize / Math.max(mOptions1.outHeight, mOptions1.outWidth).toDouble()
                    ) / Math.log(0.5)
                ).toInt().toDouble()
            ).toInt()
        }

        //Decode with inSampleSize
        val mOptions2 = BitmapFactory.Options()
        mOptions2.inSampleSize = scale
        fis = FileInputStream(file)
        val bitmap = BitmapFactory.decodeStream(fis, null, mOptions2)
        fis.close()
        Log.d("util", "Width :" + bitmap!!.width + " Height :" + bitmap.height)
        val out = FileOutputStream(destFile)
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, out)
        out.flush()
        out.close()

        destFile
    } catch (e: java.lang.Exception) {
        e.printStackTrace()
        null
    }
}

/**
 * get currency symbol form currency code
 * */
fun getCurrencySymbol(currencyCode: String): String {
    return try {
        var c= currencyCode
        if(c == "NIS"){
            c = "ILS"
        }
        val currency = Currency.getInstance(c)
        currency.symbol // Returns the currency symbol
    } catch (e: IllegalArgumentException) {
        // If an invalid currency code is provided, handle the exception
        "Invalid currency code"
    }
}