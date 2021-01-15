package com.showroom.pilatus.network

import com.showroom.pilatus.model.Wrapper
import com.showroom.pilatus.model.response.checkout.CheckoutData
import com.showroom.pilatus.model.response.home.CategoryResponse
import com.showroom.pilatus.model.response.home.Data
import com.showroom.pilatus.model.response.login.LoginResponse
import com.showroom.pilatus.model.response.login.User
import com.showroom.pilatus.model.response.ongkir.city.CityResponse
import com.showroom.pilatus.model.response.ongkir.cost.CostResponse
import com.showroom.pilatus.model.response.transaction.TransactionData
import io.reactivex.Observable
import okhttp3.MultipartBody
import retrofit2.http.*


interface ApiService {

    @GET("city")
    fun getCity(): Observable<CityResponse>

    @FormUrlEncoded
    @POST("cost")
    fun postCost(
        @Field("origin") origin: String,
        @Field("destination") destination: String,
        @Field("weight") weight: Int,
        @Field("courier") courier: String
    ): Observable<CostResponse>

    @FormUrlEncoded
    @POST("login")
    fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): Observable<Wrapper<LoginResponse>>

    @FormUrlEncoded
    @POST("user")
    fun changeProfile(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("address") address: String,
        @Field("city") city: String,
        @Field("houseNumber") houseNumber: String,
        @Field("phoneNumber") phoneNumber: String,
    ): Observable<Wrapper<User>>

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

    @GET("product")
    fun getProducts(): Observable<Wrapper<List<Data>>>

    @GET("product")
    fun getProductsByCategory(
        @Query("category_id") categoryId: Int
    ): Observable<Wrapper<List<Data>>>

    @GET("product")
    fun getProductsByName(
        @Query("name") name: String
    ): Observable<Wrapper<List<Data>>>

    @GET("transaction")
    fun getTransaction(): Observable<Wrapper<List<TransactionData>>>

    @GET("category")
    fun getCategories(): Observable<Wrapper<List<CategoryResponse>>>

    @FormUrlEncoded
    @POST("checkout")
    fun checkout(
        @Field("product_id") productId: Int,
        @Field("user_id") userId: Int,
        @Field("quantity") quantity: Int,
        @Field("total") total: Long,
        @Field("status") status: String,
        @Field("courier_type") courierType: String,
        @Field("courier_price") courierPrice: Long,
    ): Observable<Wrapper<CheckoutData>>

    @POST("logout")
    fun logout(): Observable<Wrapper<Boolean>>

    @Multipart
    @POST("user/photo")
    fun registerPhoto(@Part profileImage: MultipartBody.Part): Observable<Wrapper<Any>>
}