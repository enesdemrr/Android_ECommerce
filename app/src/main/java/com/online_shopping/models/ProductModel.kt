package com.online_shopping.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ProductModel(
    @SerializedName("Products")
    val products: List<Product>?
):Serializable

data class Product(
    @SerializedName("bilgiler")
    val bilgiler: List<ProductBilgiler>?,
    @SerializedName("durum")
    val durum: Boolean?,
    @SerializedName("mesaj")
    val mesaj: String?,
    @SerializedName("total")
    val total: String?
):Serializable

data class ProductBilgiler (
    @SerializedName("brief")
    val brief: String?,
    @SerializedName("campaign")
    val campaign: Campaign?,
    @SerializedName("campaignDescription")
    val campaignDescription: String?,
    @SerializedName("campaignTitle")
    val campaignTitle: String?,
    @SerializedName("categories")
    val categories: List<Category>?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("image")
    val image: Boolean?,
    @SerializedName("images")
    val images: List<İmage>?,
    @SerializedName("likes")
    val likes: Likes?,
    @SerializedName("price")
    val price: String?,
    @SerializedName("productId")
    val productId: String?,
    @SerializedName("productName")
    val productName: String?,
    @SerializedName("saleInformation")
    val saleInformation: SaleInformation?
):Serializable

data class Campaign(
    @SerializedName("campaignType")
    val campaignType: String?,
    @SerializedName("campaignTypeId")
    val campaignTypeId: String?
):Serializable

data class Category(
    @SerializedName("categoryId")
    val categoryId: String?,
    @SerializedName("categoryName")
    val categoryName: String?
):Serializable

data class İmage(
    @SerializedName("normal")
    val normal: String?,
    @SerializedName("thumb")
    val thumb: String?
):Serializable

data class Likes(
    @SerializedName("dislike")
    val dislike: Int?,
    @SerializedName("like")
    val like: Like?
):Serializable

data class SaleInformation(
    @SerializedName("saleType")
    val saleType: String?,
    @SerializedName("saleTypeId")
    val saleTypeId: String?
):Serializable

data class Like(
    @SerializedName("ortalama")
    val ortalama: String?,
    @SerializedName("oy_toplam")
    val oyToplam: String?
):Serializable