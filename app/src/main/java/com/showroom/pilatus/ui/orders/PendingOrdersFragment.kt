package com.showroom.pilatus.ui.orders

//import com.showroom.pilatus.adapter.PendingOrdersListAdapter
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.showroom.pilatus.adapter.PendingOrdersListAdapter
import com.showroom.pilatus.databinding.FragmentToPayBinding
import com.showroom.pilatus.model.response.transaction.TransactionData
import com.showroom.pilatus.ui.payment.BankActivity


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

            val dividerItemDecoration = DividerItemDecoration(
                binding.recyclerViewPendingOrders.context,
                layoutManager.orientation
            )

            binding.recyclerViewPendingOrders.addItemDecoration(dividerItemDecoration)
            binding.recyclerViewPendingOrders.layoutManager = layoutManager
            binding.recyclerViewPendingOrders.adapter = adapter

            adapter!!.setOnItemClickCallback(object : PendingOrdersListAdapter.OnItemClickCallback {
                override fun onItemClicked(transactionResponseItem: TransactionData) {
                    val toDetail = Intent(activity, DetailOrderActivity::class.java)
                    toDetail.putExtra("order", transactionResponseItem)
                    startActivity(toDetail)
                }

                override fun onPayClicked(id: String) {
                    val intent = Intent(activity, BankActivity::class.java)
                    intent.putExtra("payment_id", id)
                    startActivity(intent)
                }
            })
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}