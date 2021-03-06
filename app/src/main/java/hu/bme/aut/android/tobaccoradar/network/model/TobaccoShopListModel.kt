package hu.bme.aut.android.tobaccoradar.network.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TobaccoShopListModel(
    val id: Int,
    val name: String,
    val address: String,
    val longitude: Double,
    val latitude: Double,
    val isOpen: Boolean
)