package com.showroom.pilatus.ui.changeProfile

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.gson.Gson
import com.showroom.pilatus.PilatusShowroom
import com.showroom.pilatus.R
import com.showroom.pilatus.databinding.FragmentChangeProfileBinding
import com.showroom.pilatus.model.response.login.User
import com.showroom.pilatus.ui.login.LoginPresenter

class ChangeProfileFragment : Fragment(), ProfileContract.View {

    private var _binding: FragmentChangeProfileBinding? = null
    private val binding get() = _binding!!

    private lateinit var presenter: ProfilePresenter
    private var progressDialog: Dialog? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChangeProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter = ProfilePresenter(this)

        initViews()

        val data = Gson().fromJson(PilatusShowroom.getApp().getUser(), User::class.java)

        Glide.with(requireContext())
            .load(data.picturePath ?: data.profilePhotoUrl)
            .apply(RequestOptions.circleCropTransform())
            .into(binding.circleImageView)

        binding.tfName.editText?.setText(data.name)
        binding.tfEmail.editText?.setText(data.email)
        binding.tfAddress.editText?.setText(data.address)
        binding.tfHouseNo.editText?.setText(data.houseNumber)
        binding.tfPhone.editText?.setText(data.phoneNumber)
        binding.tfCity.editText?.setText(data.city)

        binding.btnChangeProfile.setOnClickListener {
            val edtName = binding.tfName.editText?.text.toString()
            val edtEmail = binding.tfEmail.editText?.text.toString()
            val edtAddress = binding.tfAddress.editText?.text.toString()
            val edtHouseNo = binding.tfHouseNo.editText?.text.toString()
            val edtPhone = binding.tfPhone.editText?.text.toString()
            val edtCity = binding.tfCity.editText?.text.toString()

            when {
                edtName.isEmpty() -> {
                    binding.tfName.error = "Name field cannot be empty !"
                    return@setOnClickListener
                }
                edtEmail.isEmpty() -> {
                    binding.tfEmail.error = "Email field cannot be empty !"
                    return@setOnClickListener
                }
                edtAddress.isEmpty() -> {
                    binding.tfAddress.error = "Address field cannot be empty !"
                    return@setOnClickListener
                }
                edtHouseNo.isEmpty() -> {
                    binding.tfHouseNo.error = "House Number field cannot be empty !"
                    return@setOnClickListener
                }
                edtPhone.isEmpty() -> {
                    binding.tfPhone.error = "Phone Number field cannot be empty !"
                    return@setOnClickListener
                }
                edtCity.isEmpty() -> {
                    binding.tfCity.error = "City field cannot be empty !"
                    return@setOnClickListener
                }
            }

            presenter.changeProfile(
                edtName, edtEmail, edtAddress, edtCity, edtHouseNo, edtPhone
            )
        }

        binding.topAppBar.setNavigationOnClickListener {
            activity?.onBackPressed()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initViews() {
        progressDialog = Dialog(requireActivity())
        val dialogLayout = layoutInflater.inflate(R.layout.dialog_loader, null)

        progressDialog?.let {
            it.setContentView(dialogLayout)
            it.setCancelable(false)
            it.window?.setBackgroundDrawableResource(android.R.color.transparent)
        }
    }

    override fun onChangeProfileSuccess(user: User) {
        val gson = Gson()
        val json = gson.toJson(user)

        PilatusShowroom.getApp().setUser(json)

        findNavController().navigate(R.id.action_changeProfileFragment2_to_navigationProfile)
    }

    override fun onChangeProfileFailed(message: String) {
        Toast.makeText(activity, "Gagal: $message", Toast.LENGTH_SHORT).show()
    }

    override fun showLoading() {
        progressDialog?.show()
    }

    override fun dismissLoading() {
        progressDialog?.dismiss()
    }

}