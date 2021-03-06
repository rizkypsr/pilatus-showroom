package com.showroom.pilatus.ui.changeProfile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.showroom.pilatus.databinding.FragmentChangeProfileBinding

class ChangeProfileFragment : Fragment() {

    private var _binding: FragmentChangeProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChangeProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnEditProfileName.setOnClickListener {
            val name = binding.textProfileName.text as String
            val toEditNameFragment =
                ChangeProfileFragmentDirections.actionChangeProfileFragmentToEditNameFragment(name)
            it.findNavController().navigate(toEditNameFragment)
        }

        binding.btnEditProfileEmail.setOnClickListener {
            val email = binding.textProfileEmail.text as String
            val toEditNameFragment =
                ChangeProfileFragmentDirections.actionChangeProfileFragmentToEditEmailFragment(email)
            it.findNavController().navigate(toEditNameFragment)
        }

        binding.btnEditProfilePhone.setOnClickListener {
            val phone = binding.textProfilePhone.text as String
            val toEditProfileFragment =
                ChangeProfileFragmentDirections.actionChangeProfileFragmentToEditPhoneFragment(phone)
            it.findNavController().navigate(toEditProfileFragment)
        }

        binding.btnEditProfileAddress.setOnClickListener {
            val address = binding.textProfileAddress.text as String
            val toEditAddressFragment =
                ChangeProfileFragmentDirections.actionChangeProfileFragmentToEditAddressFragment(address)
            it.findNavController().navigate(toEditAddressFragment)
        }

        binding.topAppBar.setNavigationOnClickListener {
            activity?.onBackPressed()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}