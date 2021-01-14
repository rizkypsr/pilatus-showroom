package com.showroom.pilatus.ui.orders

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.showroom.pilatus.adapter.PendingOrdersListAdapter
//import com.showroom.pilatus.adapter.PendingOrdersListAdapter
import com.showroom.pilatus.databinding.FragmentToPayBinding
import com.showroom.pilatus.model.response.transaction.TransactionData

class PendingOrdersFragment : Fragment() {

    private var _binding: FragmentToPayBinding? = null
    private val binding get() = _binding!!
    private var adapter: PendingOrdersListAdapter? = null

    private var inProgressList: ArrayList<TransactionData>? = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentToPayBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        inProgressList = arguments?.getParcelableArrayList("data")

        if (!inProgressList.isNullOrEmpty()) {
            adapter = PendingOrdersListAdapter(inProgressList!!)
            val layoutManager = LinearLayoutManager(activity)
            binding.recyclerViewPendingOrders.layoutManager = layoutManager
            binding.recyclerViewPendingOrders.adapter = adapter

            adapter!!.setOnItemClickCallback(object : PendingOrdersListAdapter.OnItemClickCallback {
                override fun onItemClicked(transactionResponseItem: TransactionData) {
                    val toDetail = Intent(activity, DetailOrderActivity::class.java)
                    toDetail.putExtra("order", transactionResponseItem)
                    startActivity(toDetail)
                }
            })
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}