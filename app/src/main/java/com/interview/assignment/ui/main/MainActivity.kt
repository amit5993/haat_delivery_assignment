package com.interview.assignment.ui.main

import android.content.Context
import androidx.annotation.IdRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.application.base.AppBaseActivity
import com.ashokvarma.bottomnavigation.BottomNavigationBar
import com.ashokvarma.bottomnavigation.BottomNavigationItem
import com.interview.assignment.R
import com.interview.assignment.databinding.ActivityMainBinding
import com.interview.assignment.ui.fragment.HomeFragment
import com.interview.assignment.ui.fragment.OrderHistoryFragment

class MainActivity : AppBaseActivity<ActivityMainBinding, MainActivityViewModel>(), BottomNavigationBar.OnTabSelectedListener {

    private var unselectColor = R.color.colorGray
    private var selectColor = R.color.colorAccent

    private var fragments: ArrayList<Fragment> = arrayListOf()

    override fun setViewBinding() = ActivityMainBinding.inflate(layoutInflater)

    override fun setViewModel() = MainActivityViewModel.newInstance(this)

    /**
     * setup bottom navigation with custom design
     * */
    override fun initView() {
        binding.navigation.clearAll()
        binding.navigation.setMode(BottomNavigationBar.MODE_FIXED)
            .setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC)
            .setBarBackgroundColor(R.color.colorTransparent)
            .setActiveColor(selectColor).apply { inActiveColor = unselectColor }
            .addItem(
                BottomNavigationItem(R.drawable.blank, "")
                    .setInactiveIconResource(R.drawable.blank)
            )
            .addItem(
                BottomNavigationItem(R.drawable.blank, "")
                    .setInactiveIconResource(R.drawable.blank)
            )
            .addItem(
                BottomNavigationItem(R.drawable.blank, "")
                    .setInactiveIconResource(R.drawable.blank)
            )
            .addItem(
                BottomNavigationItem(R.drawable.blank, "")
                    .setInactiveIconResource(R.drawable.blank)
            )
            .addItem(
                BottomNavigationItem(R.drawable.blank, "")
                    .setInactiveIconResource(R.drawable.blank)
            )
            .setTabSelectedListener(this)
            .setFirstSelectedPosition(1)
            .initialise()

        fragments.clear()
        fragments.addAll(loadFragments())
        replaceFragment(binding.fragmentContainer.id, fragments[1])
        setTabText(0)
    }

    override fun initOnClick() {

    }

    private fun replaceFragment(@IdRes id: Int, fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(id, fragment).commit()
    }

    /**
     * change color on selected tab
     * */
    private fun setTabText(position: Int) {

        binding.tvOne.setTextColor(
            ContextCompat.getColor(
                this, if (position == 0) {
                    selectColor
                } else {
                    unselectColor
                }
            )
        )
        binding.tvTwo.setTextColor(
            ContextCompat.getColor(
                this, if (position == 1) {
                    selectColor
                } else {
                    unselectColor
                }
            )
        )
        binding.tvThree.setTextColor(
            ContextCompat.getColor(
                this, if (position == 2) {
                    selectColor
                } else {
                    unselectColor
                }
            )
        )
        binding.tvFour.setTextColor(
            ContextCompat.getColor(
                this, if (position == 3) {
                    selectColor
                } else {
                    unselectColor
                }
            )
        )
        binding.tvFive.setTextColor(
            ContextCompat.getColor(
                this, if (position == 4) {
                    selectColor
                } else {
                    unselectColor
                }
            )
        )

        binding.imgOne.setColorFilter(
            ContextCompat.getColor(
                this, if (position == 0) {
                    selectColor
                } else {
                    unselectColor
                }
            )
        )
        binding.imgTwo.setColorFilter(
            ContextCompat.getColor(
                this, if (position == 1) {
                    selectColor
                } else {
                    unselectColor
                }
            )
        )
        binding.imgThree.setColorFilter(
            ContextCompat.getColor(
                this, if (position == 2) {
                    selectColor
                } else {
                    unselectColor
                }
            )
        )
        binding.imgFour.setColorFilter(
            ContextCompat.getColor(
                this, if (position == 3) {
                    selectColor
                } else {
                    unselectColor
                }
            )
        )
        binding.imgFive.setColorFilter(
            ContextCompat.getColor(
                this, if (position == 4) {
                    selectColor
                } else {
                    unselectColor
                }
            )
        )

    }

    /**
     * Update fragment here
     * */
    override fun onTabSelected(position: Int) {
        setTabText(position)
        if (position < fragments.size) {
            val fm = supportFragmentManager
            val ft = fm.beginTransaction()
            val from = fm.findFragmentById(binding.fragmentContainer.id)
            val fragment = fragments[position]
            if (fragment.isAdded) {
                ft.hide(from!!).show(fragment)
            } else {
                ft.hide(from!!).add(binding.fragmentContainer.id, fragment)
                if (fragment.isHidden) {
                    ft.show(fragment)
                }
            }
            ft.commitAllowingStateLoss()
        }
    }

    override fun onTabUnselected(position: Int) {
        if (position < fragments.size) {
            val fm = supportFragmentManager
            val ft = fm.beginTransaction()
            val fragment = fragments[position]
            ft.hide(fragment)
            ft.commitAllowingStateLoss()
        }
    }

    override fun onTabReselected(position: Int) {
        // Nothing
    }

    /**
     * Add all fragment
     * */
    private fun loadFragments(): ArrayList<Fragment> {
        fragments.add(HomeFragment.newInstance())
        fragments.add(HomeFragment.newInstance())
//        fragments.add(OrderDetailsFragment.newInstance())
        fragments.add(HomeFragment.newInstance())
        fragments.add(OrderHistoryFragment.newInstance())
        fragments.add(HomeFragment.newInstance())
        return fragments
    }

    override fun appOnBackPressed() {
        finish()
    }

}