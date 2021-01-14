package com.showroom.pilatus.ui.orders

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.showroom.pilatus.adapter.PassOrdersListAdapter
//import com.showroom.pilatus.adapter.PassOrdersListAdapter
import com.showroom.pilatus.databinding.FragmentCompletedOrdersBinding
import com.showroom.pilatus.model.response.transaction.TransactionData

class PassOrdersFragment : Fragment() {

    private var _binding: FragmentCompletedOrdersBinding? = null
    private val binding get() = _binding!!

    private var adapter: PassOrdersListAdapter? = null
    private var passOrderList: ArrayList<TransactionData>? = ArrayList()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCompletedOrdersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        passOrderList = arguments?.getParcelableArrayList("data")

        if (!passOrderList.isNullOrEmpty()) {
            adapter = PassOrdersListAdapter(passOrderList!!)
            val layoutManager = LinearLayoutManager(activity)
            binding.recyclerViewPassOrders.layoutManager = layoutManager
            binding.recyclerViewPassOrders.adapter = adapter

            adapter!!.setOnItemClickCallback(object : PassOrdersListAdapter.OnItemClickCallback {
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