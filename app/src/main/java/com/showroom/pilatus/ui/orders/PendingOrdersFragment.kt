package com.showroom.pilatus.ui.orders

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.showroom.pilatus.R
import com.showroom.pilatus.adapter.PendingOrdersListAdapter
import com.showroom.pilatus.databinding.FragmentOrdersBinding
import com.showroom.pilatus.databinding.FragmentToPayBinding
import com.showroom.pilatus.model.response.transaction.TransactionResponseItem

class PendingOrdersFragment : Fragment(), PendingOrdersListAdapter.OnItemClickCallback {


    private var _binding: FragmentToPayBinding? = null
    private val binding get() = _binding!!
    private var adapter: PendingOrdersListAdapter? = null

    var inProgressList: ArrayList<TransactionResponseItem>? = ArrayList()

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