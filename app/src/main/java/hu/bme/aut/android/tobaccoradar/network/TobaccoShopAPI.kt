package hu.bme.aut.android.tobaccoradar.network

import hu.bme.aut.android.tobaccoradar.network.model.TobaccoShopListModel
import hu.bme.aut.android.tobaccoradar.network.model.TobaccoShopModel
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Streaming

interface TobaccoShopAPI {
    @GET("/tobbacoshop")
    fun getAllTobaccoShop(): Call<MutableList<TobaccoShopListModel>>

    @GET("/tobbacoshop/{id}")
    fun getSelectedTobaccoShop(@Path("id") id: Int): Call<TobaccoShopModel>

    @GET("/tobbacoshop/{id}/image")
    @Streaming
    fun getSelectedTobaccoShopImage(@Path("id") id: Int): Call<ResponseBody>

}