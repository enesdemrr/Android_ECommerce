package com.online_shopping.services

import com.online_shopping.models.OrderModel
import com.online_shopping.models.ProductModel
import com.online_shopping.models.RegisterModel
import com.online_shopping.models.UserModel
import com.online_shopping.utils.ApiClient
import retrofit2.Call
import retrofit2.http.*

interface Service {
    @GET("userLogin.php")
    fun userLogin(
        @Query("userEmail") userEmail: String,
        @Query("userPass") password: String,
        @Query("ref") ref: String = ApiClient.ref,
        @Query("face") face: String = "no"
    ):Call<UserModel>
    @GET("product.php")
    fun getProduct(
        @Query("ref") ref : String = ApiClient.ref,
        @Query("start") start : String = "0"
    ):Call<ProductModel>
    @GET("userRegister.php")
    fun userRegister(
        @Query("userName") userName: String,
        @Query("userSurname") userSurname: String,
        @Query("userPhone") userPhone: String,
        @Query("userMail") userMail: String,
        @Query("userPass") userPass: String,
        @Query("ref") ref: String = ApiClient.ref
    ):Call<RegisterModel>
    //customerId=12&productId=12&html=12
    @GET("orderForm.php")
    fun productOrder(
        @Query("customerId") customerId :String,
        @Query("productId") productId: String,
        @Query("ref") ref: String = ApiClient.ref,
        @Query("html") html: String = productId
    ):Call<OrderModel>
}