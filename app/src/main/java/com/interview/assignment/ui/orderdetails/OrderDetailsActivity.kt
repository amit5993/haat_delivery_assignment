package com.interview.assignment.ui.orderdetails

import android.content.Intent
import com.application.base.AppBaseActivity
import com.google.gson.Gson
import com.interview.assignment.R
import com.interview.assignment.databinding.ActivityOrderDetailsBinding
import com.interview.assignment.model.OrderHistoryResponse
import com.interview.assignment.pref.AppPref.SERVER_BASE_URL
import com.interview.assignment.ui.adapters.ProductAdapter
import com.interview.assignment.ui.map.MapActivity
import com.interview.assignment.util.dateTimeFormatterChange
import com.interview.assignment.util.getCurrencySymbol
import com.interview.assignment.util.loadImage

class OrderDetailsActivity :
    AppBaseActivity<ActivityOrderDetailsBinding, OrderDetailsActivityViewModel>() {

    override fun setViewBinding() = ActivityOrderDetailsBinding.inflate(layoutInflater)

    override fun setViewModel() = OrderDetailsActivityViewModel.newInstance(this)

    override fun initView() {

        val json = intent.getStringExtra("JSON")
        val item = Gson().fromJson(json, OrderHistoryResponse::class.java);

        var c = ""
        if (item.currencyData != null) {
            c = item.currencyData.symbol ?: ""
        }


        binding.tvId.text = (item.id.toString() ?: "")
        binding.tvDate.text = dateTimeFormatterChange(item.orderDate ?: "", "yyyy-MM-dd hh:mm a")
        binding.tvReference.text = (item.referenceNumber ?: "")
        binding.tvArrived.text = dateTimeFormatterChange(item.arriveDate ?: "", "hh:mm a")
        binding.tvSubTotal.text = getCurrencySymbol(c).plus(item.restaurantPrice.toString() ?: "")
        binding.tvTotal.text = getCurrencySymbol(c).plus(item.price.toString() ?: "")
        binding.tvServiceFee.text = getCurrencySymbol(c).plus(item.serviceFee.toString() ?: "")

        if (item.deliveryFeeDetails != null) {
            binding.tvDeliveryFee.text =
                getCurrencySymbol(c).plus(item.deliveryFeeDetails.userFee.toString() ?: "")
        }

        if (item.restaurantDetail.details != null) {
            binding.tvTitle.text = (item.restaurantDetail.details.name_EN ?: "")
        }

        if (item.restaurantDetail.details.images.isNotEmpty()) {
            binding.imgTitle.loadImage(SERVER_BASE_URL +item.restaurantDetail.details.images[0].serverImageUrl)
        }

        if (item.restaurantDetail.products.isNotEmpty()) {
            binding.rvProduct.adapter = ProductAdapter(c).apply {
                addAll(item.restaurantDetail.products)
            }
        }

        binding.rlTrackOrder.setOnClickListener {
            startActivity(Intent(this, MapActivity::class.java).also {
            })
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        }

    }

    override fun initOnClick() {

    }


    override fun appOnBackPressed() {
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

}