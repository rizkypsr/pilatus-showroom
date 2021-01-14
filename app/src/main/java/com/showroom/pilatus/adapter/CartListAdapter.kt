package com.showroom.pilatus.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.showroom.pilatus.databinding.ItemListCartBinding
import com.showroom.pilatus.model.response.cart.CartItem
import com.showroom.pilatus.model.response.cart.ShoppingCart
import com.showroom.pilatus.utils.Helpers

class CartListAdapter(
    private val listNewProducts: MutableList<CartItem>
) : RecyclerView.Adapter<CartListAdapter.NewProductsViewHolder>() {

    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewProductsViewHolder {
        val binding =
            ItemListCartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NewProductsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NewProductsViewHolder, position: Int) {
        holder.bind(listNewProducts[position], position)
    }

    override fun getItemCount(): Int = listNewProducts.size


    inner class NewProductsViewHolder(private val binding: ItemListCartBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(cartItem: CartItem, index: Int) {

            with(binding) {
                Glide.with(itemView.context)
                    .load(cartItem.product.picturePath)
                    .into(ivProductPhoto)

                textProductTitleCart.text = cartItem.product.name
                textProductCartPrice.text =
                    Helpers.getCurrencyIDR(cartItem.product.price.toDouble())

                tvQuantity.text = cartItem.quantity.toString() + " items"

                btnDeleteCart.setOnClickListener {
                    onItemClickCallback?.onDeleteClicked(cartItem)
                }

                itemView.setOnClickListener {
                    onItemClickCallback?.onItemClicked(cartItem)
                }
            }
        }
    }


    interface OnItemClickCallback {
        fun onItemClicked(cartItem: CartItem)
        fun onDeleteClicked(cartItem: CartItem)
    }
}