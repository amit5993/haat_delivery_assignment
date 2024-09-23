package com.interview.assignment.ui.fragment

import android.view.LayoutInflater
import android.view.View
import com.application.base.AppBaseFragment
import com.interview.assignment.R
import com.interview.assignment.databinding.FragmentHomeBinding
import com.interview.assignment.pref.AppPref.SERVER_BASE_URL
import com.interview.assignment.ui.adapters.CategoryAdapter
import com.interview.assignment.ui.adapters.TagsAdapter

class HomeFragment : AppBaseFragment<FragmentHomeBinding, HomeFragmentViewModel>() {

    companion object {
        @JvmStatic
        fun newInstance() = HomeFragment()
    }

    lateinit var tagsAdapter: TagsAdapter
    lateinit var categoryAdapter: CategoryAdapter

    override fun getViewBinding() =
        FragmentHomeBinding.inflate(LayoutInflater.from(requireContext()), null, false)

    override fun getViewModelClass() = HomeFragmentViewModel.newInstance(requireContext())

    override fun initView() {

        binding.llSkeleton.visibility = View.VISIBLE

        binding.rvTag.adapter = TagsAdapter().apply {
            addAll(arrayListOf())
        }.also {
            tagsAdapter = it
        }

        binding.rvCategory.adapter = CategoryAdapter(mContext).apply {
            addAll(arrayListOf())
        }.also {
            categoryAdapter = it
        }

        viewModel.getRestaurantData {
            // it -> is your API data
            SERVER_BASE_URL = it.appSettings!!.imageServer!!.plus("/") //setup image base url here

            binding.tvTag.text = it.tags!!.title
            tagsAdapter.addAll(it.tags.tags ?: arrayListOf())

            categoryAdapter.addAll(it.categories!!)

            binding.llSkeleton.visibility = View.GONE
        }
    }

    /**
     * manage adapter cell click here
     * */
    override fun initOnClick() {
        tagsAdapter.setItemClickListener { view, position, categories ->
            // Clicks
            when (view.id) {
                R.id.imgTag -> {

                }

                R.id.divDec -> {

                }
            }
        }
    }

}