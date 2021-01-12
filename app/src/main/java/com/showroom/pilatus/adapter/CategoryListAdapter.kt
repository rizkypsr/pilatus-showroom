package com.showroom.pilatus.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.showroom.pilatus.databinding.ItemListAllCategoryBinding
import com.showroom.pilatus.model.response.home.CategoryResponse

class CategoryListAdapter(
    private val listAllCategory: List<CategoryResponse>
) : RecyclerView.Adapter<CategoryListAdapter.AllCategoryListViewHolder>() {

    private var onItemClickCallback: CategoryListAdapter.OnItemClickCallback? = null

    fun setOnItemClickCallback(onItemClickCallback: CategoryListAdapter.OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllCategoryListViewHolder {
        val binding =
            ItemListAllCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AllCategoryListViewHolder(binding)
    }

    override fun getItemCount(): Int = listAllCategory.size

    override fun onBindViewHolder(holder: AllCategoryListViewHolder, position: Int) {
        holder.bind(listAllCategory[position])
    }

    inner class AllCategoryListViewHolder(private val binding: ItemListAllCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(category: CategoryResponse) {
            with(binding) {
//                Glide.with(itemView.context)
//                    .load(category.icon)
//                    .into(ivCategoryPhoto)

                textCategoryTitle.text = category.name

                itemView.setOnClickListener {
                    onItemClickCallback?.onItemClicked(category, category.id, category.name)
                }
            }
        }

    }

    interface OnItemClickCallback {
        fun onItemClicked(category: CategoryResponse, categoryId: Int, categoryName: String)
    }
}