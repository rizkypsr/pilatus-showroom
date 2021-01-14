package com.showroom.pilatus.ui.ongkir.cost

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.showroom.pilatus.databinding.ItemListCourierBinding
import com.showroom.pilatus.model.response.ongkir.city.Result
import com.showroom.pilatus.model.response.ongkir.cost.Cost
import com.showroom.pilatus.utils.Helpers

class OngkirListAdapter(
    private var listCourier: List<Cost>
) : RecyclerView.Adapter<OngkirListAdapter.AllCategoryListViewHolder>() {

    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllCategoryListViewHolder {
        val binding =
            ItemListCourierBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AllCategoryListViewHolder(binding)
    }

    override fun getItemCount(): Int = listCourier.size

    override fun onBindViewHolder(holder: AllCategoryListViewHolder, position: Int) {
        holder.bind(listCourier[position])
    }

    inner class AllCategoryListViewHolder(private val binding: ItemListCourierBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(courier: Cost) {
            with(binding) {

                tvType.text = "${courier.service} (${courier.cost[0].etd} hari)"
                tvPrice.text = Helpers.getCurrencyIDR(courier.cost[0].value.toDouble())

                itemView.setOnClickListener {
                    onItemClickCallback?.onItemClicked(courier)
                }
            }
        }

    }

    interface OnItemClickCallback {
        fun onItemClicked(courier: Cost)
    }
}