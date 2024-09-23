package com.interview.assignment.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.application.base.AppBaseAdapter
import com.interview.assignment.databinding.ItemOrderHistoryCellBinding
import com.interview.assignment.model.OrderHistoryResponse
import com.interview.assignment.pref.AppPref.SERVER_BASE_URL
import com.interview.assignment.util.dateTimeFormatterChange
import com.interview.assignment.util.getCurrencySymbol
import com.interview.assignment.util.loadImage

class OrderHistoryAdapter : AppBaseAdapter<OrderHistoryResponse, ItemOrderHistoryCellBinding>() {

    override fun getViewBinding(parent: ViewGroup, attachToRoot: Boolean) =
        ItemOrderHistoryCellBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            attachToRoot
        )

    override fun setClickableView(itemView: View): List<View?> = listOf(binding.llMain)


    override fun onBind(
        viewType: Int,
        view: ItemOrderHistoryCellBinding,
        position: Int,
        item: OrderHistoryResponse,
        payloads: MutableList<Any>?
    ) {
        view.run {

            var c = ""
            if (item.currencyData != null){
                c = item.currencyData.symbol ?: ""
            }

            tvId.text = (item.id.toString() ?: "")
            tvDate.text = dateTimeFormatterChange(item.orderDate ?: "", "yyyy-MM-dd hh:mm a")
            tvReference.text = (item.referenceNumber ?: "")
            tvArrived.text =dateTimeFormatterChange (item.arriveDate ?: "", "hh:mm a")
            tvSubTotal.text = getCurrencySymbol(c).plus(item.restaurantPrice.toString() ?: "")
            tvTotal.text =  getCurrencySymbol(c).plus(item.price.toString() ?: "")
            tvServiceFee.text =  getCurrencySymbol(c).plus(item.serviceFee.toString() ?: "")

            if (item.deliveryFeeDetails != null) {
                tvDeliveryFee.text =  getCurrencySymbol(c).plus(item.deliveryFeeDetails.userFee.toString() ?: "")
            }

            if(item.restaurantDetail.details != null){
                tvTitle.text = (item.restaurantDetail.details.name_EN ?: "")
            }

            if (item.restaurantDetail.details.images.isNotEmpty()){
                imgTitle.loadImage(SERVER_BASE_URL+item.restaurantDetail.details.images[0].serverImageUrl)
            }

            if (item.restaurantDetail.products.isNotEmpty()) {

                //bind product adapter
                rvProduct.adapter = ProductAdapter(c).apply {
                    addAll(item.restaurantDetail.products)
                }
            }

        }
    }

}