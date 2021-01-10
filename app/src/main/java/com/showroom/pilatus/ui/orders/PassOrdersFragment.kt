package com.showroom.pilatus.ui.orders

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.showroom.pilatus.R
import com.showroom.pilatus.adapter.PassOrdersListAdapter
import com.showroom.pilatus.adapter.PendingOrdersListAdapter
import com.showroom.pilatus.databinding.FragmentCompletedOrdersBinding
import com.showroom.pilatus.databinding.FragmentToPayBinding
import com.showroom.pilatus.model.response.transaction.TransactionResponseItem

class PassOrdersFragment : Fragment(), PassOrdersListAdapter.OnItemClickCallback {

    private var _binding: FragmentCompletedOrdersBinding? = null
    private val binding get() = _binding!!

    private var adapter: PassOrdersListAdapter? = null
    var passOrderList: ArrayList<TransactionResponseItem>? = ArrayList()


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
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemClicked(transactionResponseItem: TransactionResponseItem) {
        Toast.makeText(activity, transactionResponseItem.status, Toast.LENGTH_SHORT).show()
    }
}