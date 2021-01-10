package com.showroom.pilatus.adapter

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.showroom.pilatus.R
import com.showroom.pilatus.model.response.transaction.TransactionResponseItem
import com.showroom.pilatus.ui.orders.*

class OrdersPagerAdapter(
    private val context: Context, fm: FragmentManager
) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    var inProgressList: ArrayList<TransactionResponseItem>? = ArrayList()
    var passOrderList: ArrayList<TransactionResponseItem>? = ArrayList()

    @StringRes
    private val TAB_TITLES = intArrayOf(
        R.string.tab_text_1,
        R.string.tab_text_2,
    )

    override fun getCount(): Int = 2

    override fun getItem(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> {
                fragment = PendingOrdersFragment()
                val bundle = Bundle()
                bundle.putParcelableArrayList("data", inProgressList)
                fragment.arguments = bundle
                Log.d("JUNET", "onAdapter: $inProgressList")
            }
            1 -> {
                fragment = PassOrdersFragment()
                val bundle = Bundle()
                bundle.putParcelableArrayList("data", passOrderList)
                fragment.arguments = bundle
            }
        }
        return fragment as Fragment
    }

    override fun getPageTitle(position: Int): CharSequence {
        return context.resources.getString(TAB_TITLES[position])
    }

    fun setData(
        inProgressListParms: ArrayList<TransactionResponseItem>?,
        passOrderListParms: ArrayList<TransactionResponseItem>?
    ) {
        inProgressList = inProgressListParms
        passOrderList = passOrderListParms
    }

}