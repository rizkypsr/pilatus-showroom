package com.showroom.pilatus.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.showroom.pilatus.databinding.ItemListCategoryResultBinding
import com.showroom.pilatus.model.response.home.Data
import com.showroom.pilatus.utils.Helpers

class CategoryResultListAdapter(
    private val listNewProducts: ArrayList<Data>
) : RecyclerView.Adapter<CategoryResultListAdapter.NewProductsViewHolder>() {

    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewProductsViewHolder {
        val binding =
            ItemListCategoryResultBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return NewProductsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NewProductsViewHolder, position: Int) {
        holder.bind(listNewProducts[position])
    }

    override fun getItemCount(): Int = listNewProducts.size


    inner class NewProductsViewHolder(private val binding: ItemListCategoryResultBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(product: Data) {
            with(binding) {
//                Glide.with(itemView.context)
//                    .load(product.)
//                    .into(ivProductPhoto)

                textProductName.text = product.name
                textProductPrice.text = Helpers.getCurrencyIDR(product.price.toDouble())

                itemView.setOnClickListener {
                    onItemClickCallback?.onItemClicked(product)
                }
            }
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(product: Data)
    }
}