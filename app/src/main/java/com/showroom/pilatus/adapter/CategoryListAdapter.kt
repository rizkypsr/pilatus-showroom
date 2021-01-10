package com.showroom.pilatus.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.showroom.pilatus.databinding.ItemListCategoryBinding
import com.showroom.pilatus.model.response.home.CategoryResponse

class CategoryListAdapter(
    private val listCategory: List<CategoryResponse>
) : RecyclerView.Adapter<CategoryListAdapter.CategoryListViewHolder>() {

    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryListViewHolder {
        val binding =
            ItemListCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryListViewHolder(binding)
    }

    override fun getItemCount(): Int = listCategory.size

    override fun onBindViewHolder(holder: CategoryListViewHolder, position: Int) {
        holder.bind(listCategory[position])
    }

    inner class CategoryListViewHolder(private val binding: ItemListCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(category: CategoryResponse) {
            with(binding) {
//                Glide.with(itemView.context)
//                    .load(category.icon)
//                    .apply(RequestOptions().override(96, 96))
//                    .into(ivComputer)

                textCategory.text = category.name

                itemView.setOnClickListener {
                    onItemClickCallback?.onItemClicked(category)
                }
            }
        }

    }

    interface OnItemClickCallback {
        fun onItemClicked(category: CategoryResponse)
    }
}