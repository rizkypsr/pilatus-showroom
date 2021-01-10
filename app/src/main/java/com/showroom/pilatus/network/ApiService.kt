package com.showroom.pilatus.network

import com.showroom.pilatus.model.Wrapper
import com.showroom.pilatus.model.response.home.CategoryResponse
import com.showroom.pilatus.model.response.home.Data
import com.showroom.pilatus.model.response.home.ProductResponse
import com.showroom.pilatus.model.response.login.LoginResponse
import io.reactivex.Observable
import okhttp3.MultipartBody
import retrofit2.http.*

interface ApiService {

//    @Headers("Content-Type: application/json", "Accept: application/json")
//    @GET("product")
//    fun getProducts(): Call<Data>

//    @Headers("Content-Type: application/json", "Accept: application/json")
//    @GET("product")
//    fun getProductsByCategory(
//        @Query("category_id") categoryId: Int
//    ): Call<Data>
//

//
//    @Headers("Content-Type: application/json", "Accept: application/json")
//    @GET("user")
//    fun getUser(
//        @Header("Authorization") token: String
//    ): Call<UserResponse>

    //    @Headers("Content-Type: application/json", "Accept: application/json")
//    @POST("logout")
//    fun logout(
//        @Header("Authorization") token: String
//    ): Call<LogoutResponse>
//
    @FormUrlEncoded
    @POST("login")
    fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): Observable<Wrapper<LoginResponse>>

    @FormUrlEncoded
    @POST("register")
    fun register(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("password_confirmation") passwordConfirmation: String,
        @Field("address") address: String,
        @Field("city") city: String,
        @Field("houseNumber") houseNumber: String,
        @Field("phoneNumber") phoneNumber: String,
    ): Observable<Wrapper<LoginResponse>>

    @Multipart
    @POST("user/photo")
    fun registerPhoto(@Part profileImage: MultipartBody.Part): Observable<Wrapper<Any>>

    @GET("product")
    fun getProducts(): Observable<Wrapper<List<Data>>>

    @GET("category")
    fun getCategories(): Observable<Wrapper<List<CategoryResponse>>>
}