package com.showroom.pilatus.ui.orders

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewParent
import android.widget.Toast
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.showroom.pilatus.R
import com.showroom.pilatus.adapter.OrdersPagerAdapter
import com.showroom.pilatus.databinding.FragmentOrdersBinding
import com.showroom.pilatus.model.response.transaction.TransactionResponse
import com.showroom.pilatus.model.response.transaction.TransactionResponseItem
import com.showroom.pilatus.ui.home.HomePresenter

class OrdersFragment : Fragment(), OrderContract.View {

    private var _binding: FragmentOrdersBinding? = null
    private val binding get() = _binding!!

    private lateinit var presenter: OrderPresenter

    private var progressDialog: Dialog? = null
    private var pendingOrders: ArrayList<TransactionResponseItem>? = ArrayList()
    private var passOrders: ArrayList<TransactionResponseItem>? = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOrdersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()

        presenter = OrderPresenter(this)

        presenter.getTransaction()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initView() {
        progressDialog = Dialog(requireContext())
        val dialogLayout = layoutInflater.inflate(R.layout.dialog_loader, null)

        progressDialog?.let {
            it.setContentView(dialogLayout)
            it.setCancelable(false)
            it.window?.setBackgroundDrawableResource(android.R.color.transparent)
        }
    }

    override fun onTransactionSuccess(data: List<TransactionResponseItem>) {
        for (a in data.indices) {
            if (data[a].status.equals("ON_DELIVERY", true)
                || data[a].status.equals("PENDING", true)
            ) {
                pendingOrders?.add(data[a])
            } else if (data[a].status.equals("DELIVERY", true)
                || data[a].status.equals("CANCELLED", true)
                || data[a].status.equals("SUCCESS", true)
            ) {
                passOrders?.add(data[a])
            }
        }

        val ordersPagerAdapter =
            OrdersPagerAdapter(requireActivity(), requireActivity().supportFragmentManager)

        ordersPagerAdapter.setData(pendingOrders, passOrders)

        val viewPager = binding.viewPager

        viewPager.adapter = ordersPagerAdapter
        binding.tabs.setupWithViewPager(viewPager)
    }

    override fun onTransactionFailed(message: String) {
        Toast.makeText(activity, "Transaksi gagal di ambil. $message", Toast.LENGTH_LONG).show()
    }

    override fun showLoading() {
        progressDialog?.show()
    }

    override fun dismissLoading() {
        progressDialog?.dismiss()
    }
}