package com.interview.assignment.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.application.base.AppBaseAdapter
import com.interview.assignment.databinding.ItemTagCellBinding
import com.interview.assignment.model.Tag
import com.interview.assignment.pref.AppPref.SERVER_BASE_URL
import com.interview.assignment.util.loadImage

class TagsAdapter : AppBaseAdapter<Tag, ItemTagCellBinding>() {

    override fun getViewBinding(parent: ViewGroup, attachToRoot: Boolean) =
        ItemTagCellBinding.inflate(LayoutInflater.from(parent.context), parent, attachToRoot)

    override fun setClickableView(itemView: View): List<View?> = listOf(binding.llMain)

    override fun onBind(
        viewType: Int,
        view: ItemTagCellBinding,
        position: Int,
        item: Tag,
        payloads: MutableList<Any>?
    ) {
        view.run {
            tvTitle.text = (item.name ?: "")
            imgTag.loadImage(SERVER_BASE_URL.plus(item.images.smallServerImage))
        }
    }

}