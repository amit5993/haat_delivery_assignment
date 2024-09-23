package com.interview.assignment.ui.fragment

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.application.base.AppBaseFragment
import com.google.gson.Gson
import com.interview.assignment.R
import com.interview.assignment.databinding.FragmentOrderHistoryBinding
import com.interview.assignment.ui.adapters.OrderHistoryAdapter
import com.interview.assignment.ui.main.MainActivity
import com.interview.assignment.ui.orderdetails.OrderDetailsActivity

class OrderHistoryFragment : AppBaseFragment<FragmentOrderHistoryBinding, OrderHistoryFragmentViewModel>() {

    var page = 1

    companion object {
        @JvmStatic
        fun newInstance() = OrderHistoryFragment()
    }

    lateinit var orderHistoryAdapter: OrderHistoryAdapter

    override fun getViewBinding() = FragmentOrderHistoryBinding.inflate(LayoutInflater.from(requireContext()), null, false)

    override fun getViewModelClass() = OrderHistoryFragmentViewModel.newInstance(requireContext())

    override fun initView() {
        binding.llSkeleton.visibility = View.VISIBLE

        binding.rvOrderHistory.adapter = OrderHistoryAdapter().apply {
            addAll(arrayListOf())
        }.also {
            orderHistoryAdapter = it
        }

        viewModel.getOrderHistory(page){
            orderHistoryAdapter.addAll(it)
            binding.llSkeleton.visibility = View.GONE
        }

        binding.rvOrderHistory.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                // bind pagination logic here
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val totalItemCount = layoutManager.itemCount
                val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()

                if (lastVisibleItemPosition == totalItemCount - 1) {
                    page++
                    viewModel.getOrderHistory(page){
                        if (it.isNotEmpty()) {
                            orderHistoryAdapter.appendAll(it)
                        }else{
                            page--
                        }
                    }
                }
            }
        })
    }

    /**
     * manage adapter cell click here
     * */
    override fun initOnClick() {
        orderHistoryAdapter.setItemClickListener { view, position, orderHistoryResponse ->
            when (view.id) {
                R.id.llMain -> {
                    //open new page with send data
                    mActivity.startActivity(Intent(mActivity, OrderDetailsActivity::class.java).also {
                        it.putExtra("JSON", Gson().toJson(orderHistoryResponse))
                    })
                    mActivity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
                }

            }
        }
    }
}