package com.online_shopping.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import android.widget.Toolbar
import com.bumptech.glide.Glide
import com.online_shopping.databinding.ActivityProductDetailBinding
import com.online_shopping.models.OrderModel
import com.online_shopping.models.ProductBilgiler
import com.online_shopping.services.Service
import com.online_shopping.utils.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductDetailActivity : AppCompatActivity() {
    private lateinit var bind : ActivityProductDetailBinding
    lateinit var userId:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityProductDetailBinding.inflate(layoutInflater)
        setContentView(bind.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Products Detail"
        val product =intent.getSerializableExtra("Detail") as ProductBilgiler
        Glide.with(this).load(product.images!![0].normal.toString()).into(bind.imgDetailProduct)
        bind.tvDetailName.text = product.productName.toString()
        bind.tvDetailPrice.text = product.price.toString()+ " TL"
        bind.tvDetailDesc.text = product.description.toString()
        val productId = product.productId.toString()
        bind.button.setOnClickListener {
            if(RegisterActivity.getRegUserId() != ""){
                userId =RegisterActivity.getRegUserId().toString()
            }
            if(LoginActivity.getUserId() != ""){
                userId = LoginActivity.getUserId().toString()
            }
            product_order(userId,productId)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home->{
                onBackPressed();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }
    fun product_order(usId : String,proId :String){
        val service = ApiClient.getClient().create(Service::class.java)
        var dataService = service.productOrder(usId, proId)
        dataService.enqueue(object : Callback<OrderModel> {
            override fun onResponse(call: Call<OrderModel>, response: Response<OrderModel>) {
                if (response.isSuccessful) {
                    val productOrder= response.body()
                    if (productOrder?.order!![0]?.durum == true) {
                        Toast.makeText(this@ProductDetailActivity, "Order Successfull", Toast.LENGTH_SHORT).show()
                        bind.button.text = "Added to basket "
                        bind.button.isClickable = false
                    }
                    else{
                        Toast.makeText(this@ProductDetailActivity, "${productOrder.order!![0].mesaj.toString()}", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            override fun onFailure(call: Call<OrderModel>, t: Throwable) {
                Toast.makeText(this@ProductDetailActivity, "Error: $t " , Toast.LENGTH_SHORT).show()
            }
        })
    }
}