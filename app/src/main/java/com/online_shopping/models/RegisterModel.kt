package com.online_shopping.models
import com.google.gson.annotations.SerializedName


data class RegisterModel(
    @SerializedName("user")
    val user: List<RegisterUser>?
)

data class RegisterUser(
    @SerializedName("durum")
    val durum: Boolean?,
    @SerializedName("mesaj")
    val mesaj: String?
)