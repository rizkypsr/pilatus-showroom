package com.showroom.pilatus.ui.orders

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.showroom.pilatus.R
import com.showroom.pilatus.adapter.OrdersPagerAdapter
import com.showroom.pilatus.databinding.FragmentOrdersBinding
import com.showroom.pilatus.model.response.transaction.TransactionData

class OrdersFragment : Fragment(), OrderContract.View {

    private var _binding: FragmentOrdersBinding? = null
    private val binding get() = _binding!!

    private lateinit var presenter: OrderPresenter

    private var ordersPagerAdapter: OrdersPagerAdapter? = null
    private var progressDialog: Dialog? = null
    private var pendingOrders: ArrayList<TransactionData>? = ArrayList()
    private var passOrders: ArrayList<TransactionData>? = ArrayList()

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

    override fun onTransactionSuccess(data: List<TransactionData>) {
        for (a in data.indices) {
            if (data[a].status.equals("PENDING", true)) {
                pendingOrders?.add(data[a])
            } else if (data[a].status.equals("ON_DELIVERY", true)
                || data[a].status.equals("CANCELLED", true)
                || data[a].status.equals("DELIVERED", true)
            ) {
                passOrders?.add(data[a])
            }
        }

        ordersPagerAdapter = OrdersPagerAdapter(requireActivity())
        ordersPagerAdapter?.setData(pendingOrders, passOrders)

        val titles = arrayOf("Pending Orders", "Pass Orders")

        val viewPager = binding.viewPager
        viewPager.adapter = ordersPagerAdapter

        TabLayoutMediator(
            binding.tabs, binding.viewPager
        ) { tab: TabLayout.Tab, position: Int ->
            tab.text = titles[position]
        }.attach()

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