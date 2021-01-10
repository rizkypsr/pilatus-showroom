package com.showroom.pilatus.adapter

import android.content.Context
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.showroom.pilatus.R
import com.showroom.pilatus.ui.orders.*

class OrdersPagerAdapter(
    private val context: Context, fm: FragmentManager
) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    @StringRes
    private val TAB_TITLES = intArrayOf(
        R.string.tab_text_1,
        R.string.tab_text_2,
        R.string.tab_text_3,
        R.string.tab_text_4,
        R.string.tab_text_5
    )

    override fun getCount(): Int = 5

    override fun getItem(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = AllOrdersFragment()
            1 -> fragment = ToPayFragment()
            2 -> fragment = ToShipFragment()
            3 -> fragment = ToReceiveFragment()
            4 -> fragment = CompletedOrdersFragment()
        }
        return fragment as Fragment
    }

    override fun getPageTitle(position: Int): CharSequence {
        return context.resources.getString(TAB_TITLES[position])
    }
}