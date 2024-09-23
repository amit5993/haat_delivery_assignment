package com.interview.assignment.pref

import com.chibatching.kotpref.KotprefModel

object AppPref : KotprefModel() {

    fun clearPref() {
        preferences.edit().clear().apply()
        clear()
    }

    var BASE_URL by stringPref("https://user-app-staging.haat-apis.com/")
    var BASE_URL_ORDER by stringPref("https://user-new-app-staging.internal.haat.delivery/")
    var BASE_MQTT_URL by stringPref("https://user-app-staging.internal.haat.delivery/")
    var NEXT_DATE_START_TIME by stringPref("06:00:00")
    var SERVER_BASE_URL by stringPref("")

}