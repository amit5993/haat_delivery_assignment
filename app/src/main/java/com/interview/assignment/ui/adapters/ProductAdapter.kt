package com.interview.assignment.ui.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.application.base.AppBaseAdapter
import com.interview.assignment.R
import com.interview.assignment.databinding.ItemOrderHistoryCellBinding
import com.interview.assignment.databinding.ItemProductCellBinding
import com.interview.assignment.databinding.ItemRestaurantsCellBinding
import com.interview.assignment.databinding.ItemTagCellBinding
import com.interview.assignment.model.Categories
import com.interview.assignment.model.Currency
import com.interview.assignment.model.OrderHistoryResponse
import com.interview.assignment.model.Products
import com.interview.assignment.model.Tag
import com.interview.assignment.pref.AppPref.SERVER_BASE_URL
import com.interview.assignment.util.getCurrencySymbol
import com.interview.assignment.util.loadImage

class ProductAdapter(val currency: String) : AppBaseAdapter<Products, ItemProductCellBinding>() {

    override fun getViewBinding(parent: ViewGroup, attachToRoot: Boolean) =
        ItemProductCellBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            attachToRoot
        )

    override fun setClickableView(itemView: View): List<View?> = listOf(binding.llMain)

    override fun onBind(
        viewType: Int,
        view: ItemProductCellBinding,
        position: Int,
        item: Products,
        payloads: MutableList<Any>?
    ) {
        view.run {

            if(item.productImages.isNotEmpty()) {
                imgProduct.loadImage(SERVER_BASE_URL.plus(item.productImages[0].serverImageUrl))
            }

            if (item.name != null){
                tvName.text = (item.name.enUS ?: "")
            }

            tvQty.text = "Qty : ${item.quantity}"
            tvPrice.text =  getCurrencySymbol(currency).plus( item.price.toString())

        }
    }

}