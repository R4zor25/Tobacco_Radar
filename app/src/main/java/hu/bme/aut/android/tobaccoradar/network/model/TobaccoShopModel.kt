package hu.bme.aut.android.tobaccoradar.network.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TobaccoShopModel(
    val id: Int,
    val name: String,
    val address: String,
    val description: String,
    val isOpen: Boolean,
    val openHours: List<OpenHourModel>
)

