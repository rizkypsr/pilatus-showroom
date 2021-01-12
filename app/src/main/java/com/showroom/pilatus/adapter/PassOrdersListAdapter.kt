package com.showroom.pilatus.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.showroom.pilatus.databinding.ItemListPassOrdersBinding
import com.showroom.pilatus.databinding.ItemListPendingOrdersBinding
import com.showroom.pilatus.model.response.transaction.TransactionResponseItem
import com.showroom.pilatus.utils.Helpers
import com.showroom.pilatus.utils.Helpers.convertLongToTime

class PassOrdersListAdapter(
    private val listNewProducts: List<TransactionResponseItem>
) : RecyclerView.Adapter<PassOrdersListAdapter.NewProductsViewHolder>() {

    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewProductsViewHolder {
        val binding =
            ItemListPassOrdersBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NewProductsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NewProductsViewHolder, position: Int) {
        holder.bind(listNewProducts[position])
    }

    override fun getItemCount(): Int = listNewProducts.size


    inner class NewProductsViewHolder(private val binding: ItemListPassOrdersBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(transactionResponseItem: TransactionResponseItem) {
            with(binding) {
                Glide.with(itemView.context)
                    .load(transactionResponseItem.product.picturePath)
                    .into(ivProduct)

                tvProduct.text = transactionResponseItem.product.name
                tvOrderDate.text =
                    transactionResponseItem.product.createdAt.convertLongToTime("MMM dd, HH.mm")
                tvPrice.text =
                    Helpers.getCurrencyIDR(transactionResponseItem.product.price.toDouble())

                if (transactionResponseItem.status.equals("CANCELLED")) {
                    tvStatusOrder.visibility = View.VISIBLE
                }

                itemView.setOnClickListener {
                    onItemClickCallback?.onItemClicked(transactionResponseItem)
                }
            }
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(transactionResponseItem: TransactionResponseItem)
    }
}