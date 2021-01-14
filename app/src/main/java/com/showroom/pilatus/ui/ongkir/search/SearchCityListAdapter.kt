package com.showroom.pilatus.ui.ongkir.search

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.showroom.pilatus.databinding.ItemListCityBinding
import com.showroom.pilatus.model.response.ongkir.city.Result
import com.showroom.pilatus.model.response.ongkir.cost.Cost

class SearchCityListAdapter(
    private var listCity: List<Result>
) : RecyclerView.Adapter<SearchCityListAdapter.AllCategoryListViewHolder>() {

    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllCategoryListViewHolder {
        val binding =
            ItemListCityBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AllCategoryListViewHolder(binding)
    }

    override fun getItemCount(): Int = listCity.size

    override fun onBindViewHolder(holder: AllCategoryListViewHolder, position: Int) {
        holder.bind(listCity[position])
    }

    inner class AllCategoryListViewHolder(private val binding: ItemListCityBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(city: Result) {
            with(binding) {
                tvCity.text = "${city.type} ${city.cityName}"
                tvProvince.text = "Provinsi ${city.province}"

                itemView.setOnClickListener {
                    onItemClickCallback?.onItemClicked(city)
                }
            }
        }

    }

    fun filterList(filteredList: List<Result>) {
        listCity = filteredList
        notifyDataSetChanged()
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: Result)
    }
}