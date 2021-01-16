package com.showroom.pilatus.adapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.showroom.pilatus.model.response.transaction.TransactionData
import com.showroom.pilatus.ui.orders.PassOrdersFragment
import com.showroom.pilatus.ui.orders.PendingOrdersFragment

class OrdersPagerAdapter(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {

    private var inProgressList: ArrayList<TransactionData>? = ArrayList()
    private var passOrderList: ArrayList<TransactionData>? = ArrayList()

    override fun getItemCount() = 2

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> {
                fragment = PendingOrdersFragment()
                val bundle = Bundle()
                bundle.putParcelableArrayList("data", inProgressList)
                fragment.arguments = bundle
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

    fun setData(
        inProgressListParms: ArrayList<TransactionData>?,
        passOrderListParms: ArrayList<TransactionData>?
    ) {
        inProgressList = inProgressListParms
        passOrderList = passOrderListParms
    }

}