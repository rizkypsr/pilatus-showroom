package com.showroom.pilatus.ui.profile

import android.app.Dialog
import android.content.Intent
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
import com.showroom.pilatus.databinding.FragmentProfileBinding
import com.showroom.pilatus.model.response.login.User
import com.showroom.pilatus.ui.changeProfile.ProfilePresenter
import com.showroom.pilatus.ui.login.LoginActivity
import com.showroom.pilatus.utils.SessionManager

class ProfileFragment : Fragment(), LogoutContract.View {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private lateinit var presenter: LogoutPresenter
    private var progressDialog: Dialog? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()

        presenter = LogoutPresenter(this)

        if (!PilatusShowroom.getApp().getToken().isNullOrEmpty()) {
            val data = Gson().fromJson(PilatusShowroom.getApp().getUser(), User::class.java)

            binding.profileName.text = data.name

            Glide.with(requireContext())
                .load(data.picturePath ?: data.profilePhotoUrl)
                .apply(RequestOptions.circleCropTransform())
                .into(binding.profilePicture)

        } else {
            binding.profileName.setOnClickListener {
                val moveIntent = Intent(requireActivity(), LoginActivity::class.java)
                startActivity(moveIntent)
            }
        }

        binding.changeSettingsView.setOnClickListener {
            it.findNavController().navigate(R.id.action_navigationProfile_to_changeProfileFragment2)
        }

        binding.privacyView.setOnClickListener {
            presenter.logout()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initViews() {
        progressDialog = Dialog(requireContext())
        val dialogLayout = layoutInflater.inflate(R.layout.dialog_loader, null)

        progressDialog?.let {
            it.setContentView(dialogLayout)
            it.setCancelable(false)
            it.window?.setBackgroundDrawableResource(android.R.color.transparent)
        }
    }

    override fun onLogoutSuccess(data: Boolean) {
        PilatusShowroom.getApp().removeToken()
        startActivity(Intent(activity, LoginActivity::class.java))
        activity?.finish()
    }

    override fun onLogoutFailed(message: String) {
        Toast.makeText(activity, "Gagal: $message", Toast.LENGTH_SHORT).show()
    }

    override fun showLoading() {
        progressDialog?.show()
    }

    override fun dismissLoading() {
        progressDialog?.dismiss()
    }
}