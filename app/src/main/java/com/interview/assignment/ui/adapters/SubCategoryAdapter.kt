package com.interview.assignment.ui.adapters

import android.content.Context
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.application.base.AppBaseAdapter
import com.interview.assignment.R
import com.interview.assignment.databinding.ItemCategoryCellBinding
import com.interview.assignment.databinding.ItemRestaurantsCellBinding
import com.interview.assignment.databinding.ItemTagCellBinding
import com.interview.assignment.model.Categories
import com.interview.assignment.model.Stores
import com.interview.assignment.model.Tag
import com.interview.assignment.pref.AppPref.SERVER_BASE_URL
import com.interview.assignment.util.loadImage
import com.interview.assignment.util.loadImageCenterCrop

class SubCategoryAdapter(val context: Context) :
    AppBaseAdapter<Stores, ItemRestaurantsCellBinding>() {

    override fun getViewBinding(parent: ViewGroup, attachToRoot: Boolean) =
        ItemRestaurantsCellBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            attachToRoot
        )

    override fun setClickableView(itemView: View): List<View?> = listOf(binding.llMain)

    override fun onBind(
        viewType: Int,
        view: ItemRestaurantsCellBinding,
        position: Int,
        item: Stores,
        payloads: MutableList<Any>?
    ) {
        view.run {

            tvTitle.text = (item.name ?: "")
            tvAddress.text = (item.address ?: "")
            if (item.isNew!!) tvNew.visibility = View.VISIBLE else tvNew.visibility = View.INVISIBLE

            if (item.icon!! != null) {
                if (item.icon.smallServerImage != null) {
                    imgTag.loadImageCenterCrop(SERVER_BASE_URL.plus(item.icon.serverImage))
                }
            }

            /**
             * set dynamic width of cell
             * */
            val resources = context.resources
            val width = (resources.displayMetrics.widthPixels / 2.1).toInt()
            val layoutParams = LinearLayout.LayoutParams(width, LinearLayout.LayoutParams.WRAP_CONTENT)
            cvMain.layoutParams = layoutParams

        }
    }

}