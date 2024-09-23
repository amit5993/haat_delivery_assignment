package com.interview.assignment.model.repositorys

import com.application.base.AppBaseRepository
import com.interview.assignment.model.*
import com.interview.assignment.model.api.ApiClient
import io.reactivex.Observable


// Repository
class AppRepository : AppBaseRepository<ApiClient>(ApiClient()) {

    // Rx2AndroidNetworking API Calling
    fun getRestaurantData(): Observable<RestaurantResponse> {
        return api.getRestaurantData()
    }

    fun getMqttDetails(): Observable<MqttResponse> {
        return api.getMqttDetails()
    }

    fun getOrderHistory(page: Int): Observable<List<OrderHistoryResponse>> {
        return api.getOrderHistory(page)
    }

}