package hu.bme.aut.android.tobaccoradar.network

import hu.bme.aut.android.tobaccoradar.network.model.TobaccoShopListModel
import retrofit2.Call
import retrofit2.http.GET

interface TobaccoShopAPI {
    @GET("/tobbacoshop")
    fun getAllTobaccoShop(): Call<List<TobaccoShopListModel>>
}