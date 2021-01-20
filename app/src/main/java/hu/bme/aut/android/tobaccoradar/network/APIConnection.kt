package hu.bme.aut.android.tobaccoradar.network

import android.util.Log
import hu.bme.aut.android.tobaccoradar.network.model.TobaccoShopListModel
import hu.bme.aut.android.tobaccoradar.ui.fragments.TobaccoShopFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object APIConnection {

    var apiResponse: MutableList<TobaccoShopListModel> = mutableListOf()

    lateinit var tobaccoShopAPI: TobaccoShopAPI

    lateinit var retrofit: Retrofit

    fun getAllTobaccoShop() /*:MutableList<TobaccoShopListModel>*/ {
        val tobaccoShopList = tobaccoShopAPI.getAllTobaccoShop()
        tobaccoShopList.enqueue(object : Callback<List<TobaccoShopListModel>> {
            override fun onFailure(call: Call<List<TobaccoShopListModel>>, t: Throwable) {
            }

            override fun onResponse(
                call: Call<List<TobaccoShopListModel>>,
                response: Response<List<TobaccoShopListModel>>
            ) {
                Log.d("TAGA", "JEL,adat visszaérkezett")
                TobaccoShopFragment.tList = response.body()!!.toMutableList()
            }
        })
        //Thread.sleep(5000)
        // return apiResponse

    }

    fun initConnection() {
        retrofit = Retrofit.Builder()
            .baseUrl("https://dohanyradar.codevisionkft.hu")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()

        tobaccoShopAPI = retrofit.create(TobaccoShopAPI::class.java)
    }
}
