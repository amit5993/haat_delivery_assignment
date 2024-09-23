package com.interview.assignment.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.application.base.AppBaseAdapter
import com.interview.assignment.databinding.ItemCategoryCellBinding
import com.interview.assignment.databinding.ItemRestaurantsCellBinding
import com.interview.assignment.databinding.ItemTagCellBinding
import com.interview.assignment.model.Categories
import com.interview.assignment.model.Tag
import kotlin.coroutines.coroutineContext

class CategoryAdapter(val context: Context) :
    AppBaseAdapter<Categories, ItemCategoryCellBinding>() {

    lateinit var subCategoryAdapter: SubCategoryAdapter

    override fun getViewBinding(parent: ViewGroup, attachToRoot: Boolean) =
        ItemCategoryCellBinding.inflate(LayoutInflater.from(parent.context), parent, attachToRoot)

    override fun setClickableView(itemView: View): List<View?> = listOf(binding.tvViewAllCategory)

    override fun onBind(
        viewType: Int,
        view: ItemCategoryCellBinding,
        position: Int,
        item: Categories,
        payloads: MutableList<Any>?
    ) {
        view.run {

            tvTitle.text = (item.name ?: "")

            val rv = if (item.isSponsored!!) {

                if (item.viewAll!!)
                    tvViewAllSponsored.visibility = View.VISIBLE
                else
                    tvViewAllSponsored.visibility = View.GONE

                llCategory.visibility = View.GONE
                cvSponsoredHorizontal.visibility = View.VISIBLE

                rvSponsoredHorizontal
            } else {

                if (item.viewAll!!)
                    tvViewAllCategory.visibility = View.VISIBLE
                else
                    tvViewAllCategory.visibility = View.GONE

                llCategory.visibility = View.VISIBLE
                cvSponsoredHorizontal.visibility = View.GONE

                rvMarketHorizontalCategory
            }


            var list = item.stores!!
            llShowMore.visibility = View.GONE

            /**
             * set dynamic layout manager
             * */
            if (item.elementType!! == "MarketVerticalCategory") {
                rv.layoutManager = GridLayoutManager(context, 2)
                if(list.size > 4){
                    list = item.stores.take(4)
                    llShowMore.visibility = View.VISIBLE
                }
            } else if (item.elementType == "MarketHorizontalCategory") {
                rv.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
            } else if (item.elementType == "SuggestedMarketsCategory") {
                llCategory.visibility = View.GONE
                cvSponsoredHorizontal.visibility = View.GONE
                return
            }

            rv.adapter = SubCategoryAdapter(context).apply {
                addAll(list)
            }.also {
                subCategoryAdapter = it
            }

            llShowMore.setOnClickListener{
//                subCategoryAdapter.addAll(item.stores)

                /**
                 * set show more and hide logic
                 * */
                if(tvShowMore.text == "Show more"){
                    rv.adapter = SubCategoryAdapter(context).apply {
                        addAll(item.stores)
                    }

                    tvShowMore.text = "Hide"
                    imgArrow.rotation = 270F

                }else {
                    rv.adapter = SubCategoryAdapter(context).apply {
                        addAll(item.stores.take(4))
                    }

                    tvShowMore.text = "Show more"
                    imgArrow.rotation = 90F
                }



            }

        }
    }

}