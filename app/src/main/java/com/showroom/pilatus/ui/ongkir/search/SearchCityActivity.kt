package com.showroom.pilatus.ui.ongkir.search

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.showroom.pilatus.databinding.ActivitySearchOngkirBinding
import com.showroom.pilatus.model.response.ongkir.city.Result


class SearchCityActivity : AppCompatActivity(), SearchCityContract.View {


    private lateinit var binding: ActivitySearchOngkirBinding

    private lateinit var adapter: SearchCityListAdapter
    private lateinit var presenter: SearchCityPresenter

    private var listCity: MutableList<Result> = mutableListOf()

    companion object {
        const val EXTRA_SELECTED_CITY = "extra_selected_city"
        const val RESULT_CODE = 110
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchOngkirBinding.inflate(layoutInflater)
        setContentView(binding.root)

        presenter = SearchCityPresenter(this)

        presenter.getCity()

        binding.apply {
            refreshCity.setOnRefreshListener {
                presenter.getCity()
            }
        }

        binding.myToolbar.edtSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                filter(s.toString())
            }

        })

        binding.myToolbar.ivBarBack.setOnClickListener { finish() }

    }

    private fun filter(text: String) {
        val filteredList: MutableList<Result> = mutableListOf()
        for (item in listCity) {
            if (item.cityName.toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item)
            }
        }
        adapter.filterList(filteredList)
    }

    override fun onCitySuccess(city: List<Result>) {
        listCity.addAll(city)

        adapter = SearchCityListAdapter(listCity)
        adapter.setOnItemClickCallback(object : SearchCityListAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Result) {
                val intent = Intent()
                intent.putExtra(EXTRA_SELECTED_CITY, data)
                setResult(RESULT_CODE, intent)
                finish()
            }

        })

        binding.apply {
            rvCity.layoutManager = LinearLayoutManager(this@SearchCityActivity)
            rvCity.adapter = adapter
        }
    }

    override fun onCityFailed(message: String) {
        Log.d("JUNET", "onCityFailed: $message")
    }

    override fun showLoading() {
        binding.refreshCity.isRefreshing = true
    }

    override fun dismissLoading() {
        binding.refreshCity.isRefreshing = false
    }
}