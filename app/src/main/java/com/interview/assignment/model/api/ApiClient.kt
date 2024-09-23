package com.interview.assignment.model.api

import com.base.app.testing.model.api.ApiMethod
import com.interview.assignment.model.*
import com.interview.assignment.pref.AppPref.BASE_MQTT_URL
import com.interview.assignment.pref.AppPref.BASE_URL
import com.interview.assignment.pref.AppPref.BASE_URL_ORDER
import io.reactivex.Observable

class ApiClient : ApiMethod() {

    fun getRestaurantData(): Observable<RestaurantResponse> {
        return rxGetRequestQuery(
            BASE_URL.plus("api/user/main-page/by-area/1"),
            headers = hashMapOf("Accept-language" to "en-US"),
            queryPrm = hashMapOf("type" to "Market")
        ).getObjectObservable(RestaurantResponse::class.java)
    }

    fun getMqttDetails(): Observable<MqttResponse> {
        return rxGetRequestPath(
            BASE_MQTT_URL.plus("api/orders/driver-location-credentials"),
            headers = hashMapOf()
        ).getObjectObservable(MqttResponse::class.java)
    }

    fun getOrderHistory(page: Int): Observable<List<OrderHistoryResponse>> {
        return rxGetRequestPath(
            BASE_URL_ORDER.plus("api/Orders/v2/GetHistoryOrders/{page}"),
            headers = hashMapOf("Authorization" to "bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiI5OTI0OTk2Ni0yMjlmLTRmOWMtODBiNi04ZjMzOWZhYmJmYWYiLCJqdGkiOiIzOTA2N2FlNi1mNzQyLTQ4YzItYmYzZC01YzI5Njc1NGMxN2YiLCJleHAiOjE4ODQyNDUwNzMsImF1ZCI6Imh0dHBzOi8vdXNlci1hcHAtc3RhZ2luZy5pbnRlcm5hbC5oYWF0LmRlbGl2ZXJ5In0.eOSsxHcTKrBDAqK5Z6NYf58MM86I4AjqsdytE97Bwes"),
            pathPrm = "page",
            body = page.toString()
        ).getObjectListObservable(OrderHistoryResponse::class.java)
    }

}
