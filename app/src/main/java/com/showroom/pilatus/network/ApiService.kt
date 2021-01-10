package com.showroom.pilatus.network

import com.showroom.pilatus.model.Wrapper
import com.showroom.pilatus.model.response.checkout.CheckoutResponse
import com.showroom.pilatus.model.response.home.CategoryResponse
import com.showroom.pilatus.model.response.home.Data
import com.showroom.pilatus.model.response.home.ProductResponse
import com.showroom.pilatus.model.response.login.LoginResponse
import com.showroom.pilatus.model.response.transaction.TransactionResponse
import com.showroom.pilatus.model.response.transaction.TransactionResponseItem
import io.reactivex.Observable
import okhttp3.MultipartBody
import retrofit2.http.*

interface ApiService {

//    @Headers("Content-Type: application/json", "Accept: application/json")
//    @GET("product")
//    fun getProducts(): Call<Data>


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

    @GET("product")
    fun getProductsByCategory(
        @Query("category_id") categoryId: Int
    ): Observable<Wrapper<List<Data>>>

    @GET("transaction")
    fun getTransaction(): Observable<Wrapper<List<TransactionResponseItem>>>

    @GET("category")
    fun getCategories(): Observable<Wrapper<List<CategoryResponse>>>

    @FormUrlEncoded
    @POST("checkout")
    fun checkout(
        @Field("product_id") product_id: String,
        @Field("user_id") user_id: String,
        @Field("quantity") quantity: String,
        @Field("total") total: String,
        @Field("status") status: String,
    ): Observable<Wrapper<CheckoutResponse>>
}