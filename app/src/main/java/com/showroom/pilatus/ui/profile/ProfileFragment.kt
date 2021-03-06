package com.showroom.pilatus.ui.profile

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.gson.Gson
import com.showroom.pilatus.PilatusShowroom
import com.showroom.pilatus.R
import com.showroom.pilatus.databinding.FragmentProfileBinding
import com.showroom.pilatus.model.auth.logout.LogoutResponse
import com.showroom.pilatus.model.profile.UserResponse
import com.showroom.pilatus.model.response.login.User
import com.showroom.pilatus.network.APIConfig
import com.showroom.pilatus.network.ApiService
import com.showroom.pilatus.ui.login.LoginActivity
import com.showroom.pilatus.utils.SessionManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private lateinit var sessionManager: SessionManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Check if user already logged in
        if (!PilatusShowroom.getApp().getToken().isNullOrEmpty()) {

            val data = Gson().fromJson(PilatusShowroom.getApp().getUser(), User::class.java)

            binding.profileName.text = data.name
            Glide.with(view)
                .load(data.profilePhotoUrl)
                .apply(RequestOptions.circleCropTransform())
                .into(binding.profilePicture)


            binding.cardViewSettings.visibility = View.VISIBLE
            binding.cardViewPrivacy.visibility = View.VISIBLE
        } else {
            binding.profileName.setOnClickListener {
                val moveIntent = Intent(requireActivity(), LoginActivity::class.java)
                startActivity(moveIntent)
            }
        }

        binding.changeSettingsView.setOnClickListener {
            it.findNavController().navigate(R.id.action_navigationProfile_to_edit_profile_graph)
        }

        binding.privacyView.setOnClickListener {
            //logout()
        }
    }

//    private fun getUserProfile() {
//
//        val apiService =
//            APIConfig.getRetrofitClient(requireActivity()).create(ApiService::class.java)
//        val token: String? = sessionManager.fetchAuthToken()
//
//        if (token != null) {
//            apiService.getUser(token).enqueue(object : Callback<UserResponse> {
//                override fun onResponse(
//                    call: Call<UserResponse>,
//                    response: Response<UserResponse>
//                ) {
//                    val user = response.body()
//
//                    if (user != null) {
//                        Log.d("JUNET", "onResponse: ${user!!.data.name}")
//                    }
//                }
//
//                override fun onFailure(call: Call<UserResponse>, t: Throwable) {
//                    Log.d("JUNET", "onFailure: ${t.message}")
//                }
//            })
//        }
//    }

//    private fun logout() {
//
//        val apiService =
//            APIConfig.getRetrofitClient(requireActivity()).create(ApiService::class.java)
//
//        Log.d("JUNET", "onResponse: ${sessionManager.fetchAuthToken()}")
//
//        var token = sessionManager.fetchAuthToken()
//
//        apiService.logout("Bearer " + token)
//            .enqueue(object : Callback<LogoutResponse> {
//                override fun onResponse(
//                    call: Call<LogoutResponse>,
//                    response: Response<LogoutResponse>
//                ) {
//                    Log.d("JUNET", "onResponse: ${response.body()}")
//                    sessionManager.removeToken()
//                    activity!!.recreate()
//                }
//
//                override fun onFailure(call: Call<LogoutResponse>, t: Throwable) {
//                    Log.d("JUNET", "onFailure: ${t.message}")
//                }
//            })
//
//    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}