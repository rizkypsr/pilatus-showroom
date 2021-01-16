package com.showroom.pilatus.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.showroom.pilatus.R
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

                if (transactionResponseItem.payment != null) {
                    btnPay.text = "Menunggu Konfirmasi"
                    btnPay.setBackgroundColor(
                        ContextCompat.getColor(
                            btnPay.context,
                            R.color.secondaryLightColor
                        )
                    )
                    btnPay.isEnabled = false
                }

                Glide.with(itemView.context)
                    .load(transactionResponseItem.product.picturePath)
                    .into(ivProduct)

                tvProduct.text = transactionResponseItem.product.name
                tvPrice.text =
                    Helpers.getCurrencyIDR(transactionResponseItem.total.toDouble())

                btnPay.setOnClickListener {
                    onItemClickCallback?.onPayClicked(
                        transactionResponseItem.id.toString(),
                    )
                }

                itemView.setOnClickListener {
                    onItemClickCallback?.onItemClicked(transactionResponseItem)
                }
            }
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(transactionResponseItem: TransactionData)
        fun onPayClicked(id: String)
    }
}