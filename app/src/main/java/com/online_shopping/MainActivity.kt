package com.online_shopping

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.recyclerview.widget.LinearLayoutManager
import com.online_shopping.adapter.ProductAdapter
import com.online_shopping.databinding.ActivityMainBinding
import com.online_shopping.models.ProductModel
import com.online_shopping.services.Service
import com.online_shopping.utils.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    lateinit var myAdapter: ProductAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = "Products"
        binding.mainRecycler.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        val service = ApiClient.getClient().create(Service::class.java)
        val call = service.getProduct()
        call.enqueue(object : Callback<ProductModel> {
            override fun onResponse(call: Call<ProductModel>,response: Response<ProductModel>) {
                val productresp = response.body()
                val listpro = productresp?.products
                if (productresp != null) {
                    listpro?.forEach {
                        myAdapter = ProductAdapter(this@MainActivity, listpro!![0].bilgiler)
                        binding.mainRecycler.adapter = myAdapter
                        binding.mainRecycler.layoutManager = LinearLayoutManager(this@MainActivity)
                    }

                }
            }

            override fun onFailure(call: Call<ProductModel>, t: Throwable) {
                Toast.makeText(
                    this@MainActivity, "Error : $t", Toast.LENGTH_SHORT).show()
            }

        })


    }
}