package com.showroom.pilatus.ui.category

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.showroom.pilatus.R
import com.showroom.pilatus.adapter.AllCategoryListAdapter
import com.showroom.pilatus.databinding.FragmentCategoryBinding
import com.showroom.pilatus.model.response.home.CategoryResponse
import com.showroom.pilatus.ui.search.ProductByCategoryActivity

class CategoryFragment : Fragment(), CategoryContract.View {

    private var _binding: FragmentCategoryBinding? = null
    private val binding get() = _binding!!

    private lateinit var presenter: CategoryPresenter
    var progressDialog: Dialog? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCategoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()

        presenter = CategoryPresenter(this)
        presenter.getCategory()
    }

    private fun initView() {
        progressDialog = Dialog(requireContext())
        val dialogLayout = layoutInflater.inflate(R.layout.dialog_loader, null)

        progressDialog?.let {
            it.setContentView(dialogLayout)
            it.setCancelable(false)
            it.window?.setBackgroundDrawableResource(android.R.color.transparent)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCategorySuccess(categoryResponse: List<CategoryResponse>) {
        binding.recyclerViewCategory.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        val categoryListAdapter = AllCategoryListAdapter(categoryResponse)
        binding.recyclerViewCategory.adapter = categoryListAdapter

        categoryListAdapter.setOnItemClickCallback(object :
            AllCategoryListAdapter.OnItemClickCallback {
            override fun onItemClicked(
                category: CategoryResponse,
                categoryId: Int,
                categoryName: String
            ) {
                val toCategoryResultActivity = Intent(activity, ProductByCategoryActivity::class.java)
                toCategoryResultActivity.putExtra("category", category)
                startActivity(toCategoryResultActivity)
            }

        })
    }

    override fun onCategoryFailed(message: String) {
    }

    override fun showLoading() {
        progressDialog?.show()
    }

    override fun dismissLoading() {
        progressDialog?.dismiss()
    }

}