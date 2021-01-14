package com.showroom.pilatus.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.showroom.pilatus.databinding.ItemListPendingOrdersBinding
import com.showroom.pilatus.model.response.transaction.TransactionData
import com.showroom.pilatus.utils.Helpers

class PendingOrdersListAdapter(
    private val listNewProducts: List<TransactionData>
) : RecyclerView.Adapter<PendingOrdersListAdapter.NewProductsViewHolder>() {

    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewProductsViewHolder {
        val binding =
            ItemListPendingOrdersBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NewProductsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NewProductsViewHolder, position: Int) {
        holder.bind(listNewProducts[position])
    }

    override fun getItemCount(): Int = listNewProducts.size


    inner class NewProductsViewHolder(private val binding: ItemListPendingOrdersBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(transactionResponseItem: TransactionData) {
            with(binding) {
                Glide.with(itemView.context)
                    .load(transactionResponseItem.product.picturePath)
                    .into(ivProduct)

                tvProduct.text = transactionResponseItem.product.name
                tvPrice.text =
                    Helpers.getCurrencyIDR(transactionResponseItem.total.toDouble())

                itemView.setOnClickListener {
                    onItemClickCallback?.onItemClicked(transactionResponseItem)
                }
            }
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(transactionResponseItem: TransactionData)
    }
}