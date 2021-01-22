package hu.bme.aut.android.tobaccoradar.network.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class OpenHourModel(
    val day: Int,
    val openTime: String,
    val closeTime: String
)
