package hu.bme.aut.android.tobaccoradar.network

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.lifecycle.MutableLiveData
import hu.bme.aut.android.tobaccoradar.network.model.TobaccoShopListModel
import hu.bme.aut.android.tobaccoradar.network.model.TobaccoShopModel
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object APIConnection {

    val tobaccoShopListAPI: MutableLiveData<MutableList<TobaccoShopListModel>> by lazy {
        MutableLiveData<MutableList<TobaccoShopListModel>>()
    }

    val selectedTobaccoShopAPI: MutableLiveData<TobaccoShopModel> by lazy {
        MutableLiveData<TobaccoShopModel>()
    }

    val selectedTobaccoShopImageAPI: MutableLiveData<Bitmap> by lazy {
        MutableLiveData<Bitmap>()
    }

    private lateinit var tobaccoShopAPI: TobaccoShopAPI

    private lateinit var retrofit: Retrofit

    private fun getAllTobaccoShop() {
        val tobaccoShopList = tobaccoShopAPI.getAllTobaccoShop()
        tobaccoShopList.enqueue(object : Callback<MutableList<TobaccoShopListModel>> {
            override fun onFailure(
                call: Call<MutableList<TobaccoShopListModel>>,
                t: Throwable
            ) {
                Log.d("API_ERROR", t.localizedMessage!!)
            }

            override fun onResponse(
                call: Call<MutableList<TobaccoShopListModel>>,
                response: Response<MutableList<TobaccoShopListModel>>
            ) {
                if (response.isSuccessful)
                    tobaccoShopListAPI.value = response.body()
            }
        })
    }

    fun getSelectedTobaccoShop(id: Int) {
        val selectedTobaccoShop = tobaccoShopAPI.getSelectedTobaccoShop(id)
        selectedTobaccoShop.enqueue(object : Callback<TobaccoShopModel> {
            override fun onFailure(
                call: Call<TobaccoShopModel>,
                t: Throwable
            ) {
                Log.d("API_ERROR", t.localizedMessage!!)
            }

            override fun onResponse(
                call: Call<TobaccoShopModel>,
                response: Response<TobaccoShopModel>
            ) {
                if (response.isSuccessful)
                    selectedTobaccoShopAPI.value = response.body()
            }
        })
    }

    fun getSelectedTobaccoShopImage(id: Int) {
        val selectedTobaccoShop = tobaccoShopAPI.getSelectedTobaccoShopImage(id)
        selectedTobaccoShop.enqueue(object : Callback<ResponseBody> {
            override fun onFailure(
                call: Call<ResponseBody>,
                t: Throwable
            ) {
                Log.d("API_ERROR", t.localizedMessage!!)
            }

            override fun onResponse(
                call: Call<ResponseBody>,
                response: Response<ResponseBody>
            ) {
                if (response.isSuccessful) {
                    selectedTobaccoShopImageAPI.value =
                        BitmapFactory.decodeStream(response.body()?.byteStream())
                }
            }
        })
    }

    fun initConnection() {
        retrofit = Retrofit.Builder()
            .baseUrl("https://dohanyradar.codevisionkft.hu")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()

        tobaccoShopAPI = retrofit.create(TobaccoShopAPI::class.java)
        getAllTobaccoShop()
    }
}
