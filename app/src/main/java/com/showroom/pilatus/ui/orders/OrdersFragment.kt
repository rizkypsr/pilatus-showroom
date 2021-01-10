package com.showroom.pilatus.ui.orders

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewParent
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.showroom.pilatus.R
import com.showroom.pilatus.adapter.OrdersPagerAdapter
import com.showroom.pilatus.databinding.FragmentOrdersBinding

class OrdersFragment : Fragment() {

    private var _binding: FragmentOrdersBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOrdersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val ordersPagerAdapter =
            OrdersPagerAdapter(requireActivity(), requireActivity().supportFragmentManager)

        val viewPager = binding.viewPager

        viewPager.adapter = ordersPagerAdapter
        binding.tabs.setupWithViewPager(viewPager)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}