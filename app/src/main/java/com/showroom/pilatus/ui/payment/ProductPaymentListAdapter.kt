package com.showroom.pilatus.ui.payment

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.showroom.pilatus.databinding.ItemListPaymentBinding
import com.showroom.pilatus.model.response.cart.CartItem
import com.showroom.pilatus.model.response.home.Data
import com.showroom.pilatus.utils.Helpers

class ProductPaymentListAdapter(
    private val listNewProducts: List<CartItem>
) : RecyclerView.Adapter<ProductPaymentListAdapter.ViewHolder>() {

    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemListPaymentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listNewProducts[position])
    }

    override fun getItemCount(): Int {
        return listNewProducts.size
    }


    inner class ViewHolder(private val binding: ItemListPaymentBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(cartItem: CartItem) {
            with(binding) {
                Glide.with(itemView.context)
                    .load(cartItem.product.picturePath)
                    .into(ivProductPhoto)

                tvProductTitle.text = cartItem.product.name
                tvProductPrice.text = Helpers.getCurrencyIDR(cartItem.product.price.toDouble())
                tvProductQuantity.text = "${cartItem.quantity} items"

                itemView.setOnClickListener {
                    onItemClickCallback?.onItemClicked(cartItem)
                }
            }
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(cartItem: CartItem)
    }
}